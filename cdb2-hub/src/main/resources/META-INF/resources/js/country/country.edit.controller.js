(function() {
  'use strict';

  angular
    .module('hub')
    .controller('CountryEditController', CountryEditController);

  CountryEditController.$inject = [
    '$uibModalInstance',
    '$translate',
    '$log',
    'moment',
    'toastr',
    'CountryAPIService',
    'country'
  ];

  function CountryEditController(
    $uibModalInstance,
    $translate,
    $log,
    moment,
    toastr,
    CountryAPIService,
    country
  ) {
    var vm = this;

    // Making week start on Sunday, in accordance with Spring Scheduler...
    vm.dayOfWeek = [
      { id: 2, value: 'global.day.mon' },
      { id: 3, value: 'global.day.tue' },
      { id: 4, value: 'global.day.wed' },
      { id: 5, value: 'global.day.thu' },
      { id: 6, value: 'global.day.fri' },
      { id: 7, value: 'global.day.sat' },
      { id: 1, value: 'global.day.sun' }
    ];

    vm.modalInstance = $uibModalInstance;

    vm.country = country;

    if (!vm.country.id) {
      //Set active to true by default
      vm.country = { active: true };
      // Set a default frequency
      vm.country.frequency = moment()
        .set('hour', 0)
        .set('minute', 0);
    } else {
      var hm = vm.country.frequency.split(':');
      if (hm.length === 2) {
        vm.country.frequency = moment()
          .set('hour', hm[0])
          .set('minute', hm[1]);
      }
    }

    vm.save = function(isValid) {
      vm.errorsFromServer = null;
      if (isValid) {
        vm.submitted = true;

        if (vm.country.frequency) {
          vm.country.frequency = moment(vm.country.frequency).format('HH:mm');
        }

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
  }
})();
