(function () {
  'use strict';

  angular.module('cdb2')
    .directive('zipUpdate', zipUpdate);
  zipUpdate.$inject = ['$log', '$http', '$translate', 'toastr', 'SERVER'];

  function zipUpdate($log, $http, $translate, toastr, SERVER) {
    return {
      restrict: 'E',
      controllerAs: 'zipCtrl',
      templateUrl: '/js/municipality/zip-update.html',
      controller: function () {
        var vm = this;
        vm.submitted = false;
        vm.upload = function () {
          if(!vm.file){
            vm.form.file.$setValidity('empty', false);
            return;
          }else{
            vm.form.file.$setValidity('empty', true);
          }
          vm.submitted = true;
          $log.info('Upload called', vm.file);
          var formData = new FormData();
          formData.append('file', vm.file);
          formData.append('filetype', 'text');
          $http.post(SERVER.API + '/municipality/update', formData, {
            transformRequest: angular.identity,
            headers: {
              'Content-Type': undefined
            }
          }).then(function () {
            $log.debug('Update successfully submitted');
            toastr.success($translate.instant('municipality.import.transmitted'));
            vm.file = null;
          })
          .catch(function (err) {
            $log.error(err);
            vm.errorsFromServer = $translate.instant(err.data.message);
          })
          .finally(function () {
            vm.submitted = false;
          });
        };
      }
    };
  }
//FIXME: See how we manage server errors and end of process on the server (long polling ?).
// We may also have to create a dedicated table containing updates status, time of last execution, errors ...
})();