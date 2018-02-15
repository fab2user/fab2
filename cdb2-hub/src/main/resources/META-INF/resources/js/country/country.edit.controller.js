(function () {
  'use strict';

  angular
    .module('hub')
    .controller('CountryEditController', CountryEditController);

  CountryEditController.$inject = ['$uibModalInstance', '$translate', '$log', 'toastr', 'CountryAPIService', 'country'];

  function CountryEditController($uibModalInstance, $translate, $log, toastr, CountryAPIService, country) {
    var vm = this;

    vm.modalInstance = $uibModalInstance;
    vm.country = country;
    //Set active to true by default
    if(!vm.country){
      vm.country = { active: true};
    }

    vm.save = function (isValid) {
      vm.errorsFromServer = null;
      if (isValid) {
        vm.submitted = true;

        CountryAPIService.save({}, vm.country).$promise.then(function () {
          $uibModalInstance.close('save');
          toastr.success($translate.instant('global.toastr.save.success'));
        }).catch(function (err) {
          $log.error(err);
          vm.errorsFromServer = $translate.instant(err.data.message);
        }).finally(function () {
          vm.submitted = false;
        });
      }
    };

  }
})();