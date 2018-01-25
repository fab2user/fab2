(function() {
  'use strict';

  angular.module('cdb2').controller('BailiffController', BailiffController);

  BailiffController.$inject = ['$log', '$http', '$translate', '$uibModal','BailiffAPIService', 'toastr', 'MunicipalityAPIService'];

  function BailiffController($log, $http, $translate, $uibModal,BailiffAPIService, toastr, MunicipalityAPIService) {
    var vm = this;

    fetchBailiffs();

    vm.new = function(){
          loadModal({});
    };

    function loadModal(bailiff) {
      var modalInstance = $uibModal.open({
        templateUrl: '/js/bailiff/bailiff.edit.html',
        controller: 'BailiffEditController as bailiffEditCtrl',
        windowClass: 'modal-hg',
        backdrop: 'static',
        resolve: {
          bailiff: bailiff,
          cities: function(){
            return MunicipalityAPIService
             .getAll();
          }
        }
      });

      modalInstance.result.then(
        function () {
          fetchBailiffs();
          // vm.tableParams.reload();
        });
    }

    function fetchBailiffs(){
      BailiffAPIService.getAll().$promise.then(function(data) {
        vm.bailiffs = data;
      });
    }

  }
})();
