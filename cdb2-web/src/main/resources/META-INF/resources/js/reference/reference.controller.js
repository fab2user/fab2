(function() {
  'use strict';

  angular.module('cdb2').controller('ReferenceController', ReferenceController);

  ReferenceController.$inject = ['$log', '$http', '$uibModal', '$translate', 'ReferenceAPIService', 'LanguageAPIService', 'toastr'];

  function ReferenceController($log, $http, $uibModal, $translate, ReferenceAPIService, LanguageAPIService, toastr) {
    var vm = this;
    vm.selectedLang = null;

    fetchLangs();

    ReferenceAPIService.getAllCompetence().$promise.then(function(data) {
      vm.competences = data;
    });
    ReferenceAPIService.getAllInstrument().$promise.then(function(data) {
      vm.instruments = data;
    });

    vm.selectLang = function(lang) {
      if (vm.selectedLang === lang) {
        vm.selectedLang = null;
      } else {
        vm.selectedLang = lang;
      }
    };

    vm.new = function() {
      loadModal({});
    };

    vm.edit = function() {
      loadModal(vm.selectedLang);
    };

    function loadModal(lang) {
      var modalInstance = $uibModal.open({
        templateUrl: '/js/reference/language.edit.html',
        controller: 'LanguageEditController as langEditCtrl',
        windowClass: 'sm',
        backdrop: 'static',
        resolve: {
          lang: lang
        }
      });

      modalInstance.result.then(function() {
        fetchLangs();
      });
    }

    function fetchLangs() {
      ReferenceAPIService.getAllLanguage().$promise.then(function(data) {
        vm.languages = data;
      });
    }

    vm.deleteLang = function() {
      LanguageAPIService.delete({id: vm.selectedLang.id}).$promise.then(function() {
        toastr.success($translate.instant('global.toastr.delete.success'));
        fetchLangs();
      });
    };

  }
})();
