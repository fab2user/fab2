(function () {
    'use strict';
  
    angular
      .module('hub')
      .controller('CountryController', CountryController);
  
      CountryController.$inject = ['$log','$interval', '$http', '$rootScope', '$uibModal', '$translate', 'NgTableParams', 'toastr', 'CountryAPIService', 'CountryService', 'EVENT', 'SERVER', 'STATUS'];
  
    function CountryController($log, $interval, $http, $rootScope, $uibModal, $translate, NgTableParams, toastr, CountryAPIService, CountryService, EVENT, SERVER, STATUS) {
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
        $log.info('Sync started');
        CountryService.sync(country).then(function(success){
          toastr.info($translate.instant('global.export.inprogress'));
          $rootScope.$broadcast(EVENT.XML_EXPORT, success.data);
          // start polling
          vm.startPolling(success.data.id);
        });
      };

      vm.polling = undefined;

      vm.startPolling = function(taskId){
        if(angular.isDefined(vm.polling)) return;
        vm.polling = $interval(function(){
          $http.get(SERVER.API + '/task/' + taskId).then(function(success){
            if(success.data.status === STATUS.OK){
              // If some data refresh would have to be done, it should be done here
            }
            if(success.data.status === STATUS.OK || success.data.status === STATUS.ERROR){
              $rootScope.$broadcast(EVENT.XML_EXPORT, success.data);
              vm.endPolling();
            }
          });
        }, 50000, 5);
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
  