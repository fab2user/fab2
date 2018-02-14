(function () {
    'use strict';
  
    angular
      .module('hub')
      .controller('CountryController', CountryController);
  
      CountryController.$inject = [];
  
    function CountryController() {
      var vm = this;
      vm.dummyValue = 'dummy value !';
    }
  
  })();
  