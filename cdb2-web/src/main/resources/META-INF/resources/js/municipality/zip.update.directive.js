(function() {
  'use strict';

  angular.module('cdb2').directive('zipUpdate', zipUpdate);
  zipUpdate.$inject = [
    '$log',
    '$http',
    '$translate',
    '$interval',
    'toastr',
    'SERVER',
    'EVENT',
    'STATUS'
  ];

  function zipUpdate(
    $log,
    $http,
    $translate,
    $interval,
    toastr,
    SERVER,
    EVENT,
    STATUS
  ) {
    return {
      restrict: 'E',
      controllerAs: 'zipCtrl',
      templateUrl: '/js/municipality/zip-update.html',
      controller: [
        '$rootScope',
        function($rootScope) {
          var vm = this;
          vm.submitted = false;
          vm.upload = function() {
            if (!vm.file) {
              vm.form.file.$setValidity('empty', false);
              return;
            } else {
              vm.form.file.$setValidity('empty', true);
            }
            vm.submitted = true;
            $log.info('Upload called', vm.file);
            var formData = new FormData();
            formData.append('file', vm.file);
            formData.append('filetype', 'text');
            $rootScope.$broadcast(EVENT.GEONAME_IMPORT, {
              status: STATUS.IN_PROGRESS
            });
            $http
              .post(SERVER.API + '/municipality/update', formData, {
                transformRequest: angular.identity,
                headers: {
                  'Content-Type': undefined
                }
              })
              .then(function(success) {
                $log.debug('Update successfully submitted');
                vm.file = null;
                if (success.data.id) {
                  vm.startPolling(success.data.id);
                }
              })
              .catch(function(err) {
                $log.error(err);
                vm.errorsFromServer = $translate.instant(err.data.message);
              })
              .finally(function() {
                vm.submitted = false;
              });
          };

          vm.polling = undefined;

          vm.startPolling = function(taskId) {
            if (angular.isDefined(vm.polling)) return;
            vm.polling = $interval(
              function() {
                $http
                  .get(SERVER.API + '/task/geoname/' + taskId)
                  .then(function(success) {
                    // Refresh status on the screen
                    $rootScope.$broadcast(EVENT.GEONAME_IMPORT, success.data);
                    if (
                      success.data.status === STATUS.OK ||
                      success.data.status === STATUS.ERROR
                    ) {
                      vm.endPolling();
                    }
                  });
              },
              60000,
              45
            );
          };

          vm.endPolling = function() {
            if (angular.isDefined(vm.polling)) {
              $interval.cancel(vm.polling);
              vm.polling = undefined;
            }
          };

          vm.removeFile = function() {
            delete vm.file;
          };
        }
      ]
    };
  }
  //FIXME: See how we manage server errors and end of process on the server (long polling ?).
  // We may also have to create a dedicated table containing updates status, time of last execution, errors ...
})();
