(function() {
  'use strict';

  angular.module('hub').controller('StatusController', StatusController);

  StatusController.$inject = [
    '$log',
    '$translate',
    'NgTableParams',
    'lodash',
    'moment',
    'StatusAPIService',
    'countryList',
    'statuses'
  ];

  function StatusController(
    $log,
    $translate,
    NgTableParams,
    lodash,
    moment,
    StatusAPIService,
    countryList,
    statuses
  ) {
    var vm = this;

    vm.filters = {
      ba: 'after',
      countries: countryList,
      statuses: statuses
    };

    vm.statusDetails = {
      templateUrl: '/js/country/status.details.html',
      title: $translate.instant('status.label.title'),
      sync: vm.selectedStatus
    };

    vm.searchParams = {};

    vm.tableParams = new NgTableParams(
      {
        count: 10,
        page: 1
      },
      {
        getData: function(params) {
          var sort = function(params) {
            var sortParam = params.sorting();
            if (sortParam != null) {
              if (Object.keys(sortParam).length > 0) {
                var k = Object.keys(sortParam)[0];
                return k + ',' + sortParam[k];
              }
            }
            return '';
          };
          $log.debug('url', sort(params));
          return StatusAPIService.search(
            lodash.assign(
              {
                page: params.page() - 1,
                size: params.count(),
                sort: sort(params)
              },
              vm.searchParams
            )
          ).$promise.then(function(success) {
            params.total(success.totalElements);
            return success.content;
          });
        }
      }
    );

    vm.calendar = {
      opened: false
    };

    vm.openCalendar = function() {
      vm.calendar.opened = true;
    };

    vm.search = function() {
      if (vm.filters.date) {
        delete vm.searchParams.dateBefore;
        delete vm.searchParams.dateAfter;
        var formattedDate = moment(vm.filters.date).format('YYYY-MM-DD');
        if (vm.filters.ba === 'before') {
          vm.searchParams.dateBefore = formattedDate;
        } else {
          vm.searchParams.dateAfter = formattedDate;
        }
      }
      vm.tableParams.reload();
    };

    vm.clearSearch = function() {
      vm.searchParams = {};
      // We must reset pagination before new display !!
      vm.tableParams.page(1);

      delete vm.filters.date;
      vm.tableParams.reload();
    };

    //When clicking search button, we must first reset pagination, otherwise results can be messed up !
    vm.newSearch = function() {
      // We must reset pagination before new display !!
      vm.tableParams.page(1);
      vm.search();
    };
  }
})();
