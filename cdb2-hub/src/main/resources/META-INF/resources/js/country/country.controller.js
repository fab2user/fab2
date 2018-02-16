(function () {
    'use strict';
  
    angular
      .module('hub')
      .controller('CountryController', CountryController);
  
      CountryController.$inject = ['$uibModal', '$translate', 'NgTableParams', 'toastr', 'CountryAPIService', 'CountryService'];
  
    function CountryController($uibModal, $translate, NgTableParams, toastr, CountryAPIService, CountryService) {
      var vm = this;
      fetchCountries();


      vm.edit = function(country){
        var modalInstance = $uibModal.open({
          templateUrl: '/js/country/country.edit.html',
          controller: 'CountryEditController as countryEditCtrl',
          windowClass: 'modal-md',
          backdrop: 'static',
          resolve: {
            country: country
          }
        });
        modalInstance.result.then(function () {
          fetchCountries();
        });
      };

      vm.delete = function(country){
        CountryAPIService.delete({id: country.id}).$promise.then(function(){
          toastr.success($translate.instant('global.toastr.delete.success'));
          fetchCountries();
        });
      };

      vm.sync = function(country){
        CountryService.sync(country);
      };

      function fetchCountries(){
        CountryAPIService.getAll().$promise.then(
          function(success){
            vm.tableParams = new NgTableParams({}, {dataset: success});
          }
        );
      }
    }
  })();
  