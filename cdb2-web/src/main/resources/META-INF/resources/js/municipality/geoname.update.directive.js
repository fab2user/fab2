(function () {
  'use strict';

  angular.module('cdb2').directive('geonameUpdate', geonameUpdate);
  geonameUpdate.$inject = [
    '$log',
    '$http',
    '$translate',
    '$interval',
    '$location',
    '$anchorScroll',
    'toastr',
    'SERVER',
    'EVENT',
    'STATUS'
  ];

  // FIXME: Inform user when he selects wrong file (ie file type, size)

  function geonameUpdate(
    $log,
    $http,
    $translate,
    $interval,
    $location,
    $anchorScroll,
    toastr,
    SERVER,
    EVENT,
    STATUS
  ) {
    return {
      restrict: 'E',
      controllerAs: 'geoNameCtrl',
      templateUrl: '/js/municipality/geoname-update.html',
      controller: [
        '$rootScope', '$scope',
        function ($rootScope, $scope) {
          var vm = this;
          vm.submitted = false;
          vm.uploadVisible = true;
          vm.upload = function () {
            vm.closeErrorNotification();
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
              .post(SERVER.API + '/municipality/updateGeoName', formData, {
                transformRequest: angular.identity,
                headers: {
                  'Content-Type': undefined
                }
              })
              .then(function (success) {
                $log.debug('Update successfully submitted');
                vm.file = null;
                if (success.data.id) {
                  vm.startPolling(success.data.id);
                  $location.hash('page-header');
                  $anchorScroll();
                }
              })
              .catch(function (err) {
                // As we got an error from server and we display it in the component, we don't need to display it also in notif panel
                // It's relevant to display server error in the component in this case because user is focused on the component, and not in the top of the page
                $log.error(err);
                $rootScope.$broadcast(EVENT.GEONAME_IMPORT, {
                  status: STATUS.CANCEL
                });
                vm.errorsFromServer = $translate.instant(err.data.message);
              })
              .finally(function () {
                vm.submitted = false;
              });
          };

          vm.polling = undefined;

          vm.startPolling = function (taskId) {
            if (angular.isDefined(vm.polling)) return;
            vm.polling = $interval(
              function () {
                $http
                  .get(SERVER.API + '/task/' + taskId)
                  .then(function (success) {
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

          vm.endPolling = function () {
            if (angular.isDefined(vm.polling)) {
              $interval.cancel(vm.polling);
              vm.polling = undefined;
            }
          };

          vm.removeFile = function () {
            delete vm.file;
          };

          $scope.$on(EVENT.GEONAME_IMPORT, function ($event, statusObj) {
            vm.uploadVisible = !(statusObj.status === STATUS.IN_PROGRESS);
          });

          vm.closeErrorNotification = function () {
            vm.errorsFromServer = undefined;
          };
        }
      ]
    };
  }
})();