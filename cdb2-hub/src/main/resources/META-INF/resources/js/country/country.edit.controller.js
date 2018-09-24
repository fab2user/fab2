(function() {
  'use strict';

  angular
    .module('hub')
    .controller('CountryEditController', CountryEditController);

  CountryEditController.$inject = [
    '$uibModalInstance',
    '$translate',
    '$log',
    'lodash',
    'moment',
    'toastr',
    'CountryAPIService',
    'country',
    'reference',
    'competences',
    'languages'
  ];

  function CountryEditController(
    $uibModalInstance,
    $translate,
    $log,
    lodash,
    moment,
    toastr,
    CountryAPIService,
    country,
    reference,
    competences,
    languages
  ) {
    var vm = this;

    // Making week start on Sunday, in accordance with Spring Scheduler...
    vm.dayOfWeek = [
      {
        id: 2,
        value: 'global.day.mon'
      },
      {
        id: 3,
        value: 'global.day.tue'
      },
      {
        id: 4,
        value: 'global.day.wed'
      },
      {
        id: 5,
        value: 'global.day.thu'
      },
      {
        id: 6,
        value: 'global.day.fri'
      },
      {
        id: 7,
        value: 'global.day.sat'
      },
      {
        id: 1,
        value: 'global.day.sun'
      }
    ];

    vm.modalInstance = $uibModalInstance;

    vm.country = country;
    vm.searchTypes = reference.searchTypes;
    vm.competences = competences.competences;
    vm.instruments = competences.instruments;
    vm.languages = languages;

    if (!vm.country.id) {
      //Set active to true by default
      vm.country = {
        active: true
      };
      // Set a default frequency
      vm.country.frequency = moment()
        .set('hour', 0)
        .set('minute', 0);
    } else{
      if(!vm.country.frequency){
        vm.country.frequency = moment()
        .set('hour', 0)
        .set('minute', 0);
      }
      else {
        var hm = vm.country.frequency.split(':');
        if (hm.length === 2) {
          vm.country.frequency = moment()
            .set('hour', hm[0])
            .set('minute', hm[1]);
        }
      }
    }
   

    vm.save = function(isValid) {
      vm.errorsFromServer = null;
      if (isValid) {
        vm.submitted = true;

        if (vm.country.frequency) {
          vm.country.frequency = moment(vm.country.frequency).format('HH:mm');
        }

        vm.country.batchDataUpdates = prepareCompetences(
          vm.country.batchDataUpdates
        );

        CountryAPIService.save({}, vm.country)
          .$promise.then(function() {
            $uibModalInstance.close('save');
            toastr.success($translate.instant('global.toastr.save.success'));
          })
          .catch(function(err) {
            $log.error(err);
            vm.errorsFromServer = $translate.instant(err.data.message);
          })
          .finally(function() {
            vm.submitted = false;
          });
      }
    };

    vm.searchTypeChange = function() {
      if (vm.country.searchType === 'CDB') {
        vm.country.active = false;
        vm.country.daysOfWeek = [];
        setDefaultFrequency();
      }
    };

    vm.addBatchField = function(type){
      if(type === 'LANG' && batchDataUpdatesLangAlreadyPresent(vm.country.batchDataUpdates)){
        return;
      } 
      var batchDataUpdate = {
        id: null,
        field: type,
        value: '',
        countryOfSyncId: vm.country.id
      };
      vm.country.batchDataUpdates.push(batchDataUpdate);
    }

    // vm.addCompetence = function() {
    //   var batchDataUpdate = {
    //     id: null,
    //     field: 'COMPETENCE',
    //     value: '',
    //     countryOfSyncId: vm.country.id
    //   };
    //   vm.country.batchDataUpdates.push(batchDataUpdate);
    // };

    vm.removeBatchUpdate = function(index) {
      vm.country.batchDataUpdates.splice(index, 1);
    };

    function setDefaultFrequency() {
      vm.country.frequency = moment()
        .set('hour', 0)
        .set('minute', 0);
    }

    function prepareCompetences(batchDataUpdates) {
      return lodash.map(batchDataUpdates, function(bdu) {
        if (bdu.field !== 'COMPETENCE') {
          return bdu;
        }
        if (bdu.competence && bdu.instrument) {
          bdu.value = bdu.competence + '|' + bdu.instrument;
        }
        return bdu;
      });
    }

    function batchDataUpdatesLangAlreadyPresent(batchDataUpdates){
      var langs = lodash.filter(batchDataUpdates, function(bdu){
        return bdu.field === 'LANG'
      });
      return langs.length > 0 ;
    }
  }
})();
