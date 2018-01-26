(function() {
  'use strict';

  angular.module('cdb2').controller('LanguageEditController', LanguageEditController);

  LanguageEditController.$inject = [
    '$uibModalInstance',
    '$translate',
    '$log',
    'toastr',
    'LanguageAPIService',
    'lang'
  ];

  function LanguageEditController($uibModalInstance, $translate, $log, toastr, LanguageAPIService, lang) {
    var vm = this;
    vm.modalInstance = $uibModalInstance;
    vm.lang = lang;
    vm.errorsFromServer = null;

    vm.save = function(isValid) {
      vm.errorsFromServer = null;
      if (isValid) {
        vm.submitted = true;

        LanguageAPIService.save({}, vm.lang).$promise.then(function() {
          $uibModalInstance.close('save');
          toastr.success($translate.instant('global.toastr.save.success'));
        }).catch(function(err) {
          $log.error(err);
          vm.errorsFromServer = $translate.instant(err.data.message);
        }). finally(function() {
          vm.submitted = false;
        });
      }
    };

    vm.delete = function() {
      LanguageAPIService.delete({id: vm.lang.id}).$promise.then(function() {
        toastr.success($translate.instant('global.toastr.delete.success'));
        $uibModalInstance.close('save');
      });
    };
  }
})();
