(function () {
  'use strict';

  angular
    .module('hub')
    .controller('StatusController', StatusController);

  StatusController.$inject = ['$log', 'NgTableParams', 'lodash', 'StatusAPIService', 'countryList', 'statuses'];

  function StatusController($log, NgTableParams, lodash, StatusAPIService, countryList, statuses) {
    var vm = this;

    vm.filters = {
      ba: 'after',
      countries: countryList,
      statuses: statuses
    };

    vm.searchParams = {};

    vm.tableParams = new NgTableParams({
      count: 10,
      page: 1
    }, {
      getData: function (params) {
        var sort = function (params) {
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
        return StatusAPIService.search(lodash.assign({
          page: params.page() - 1,
          size: params.count(),
          sort: sort(params)
        }, vm.searchParams)).$promise.then(function (success) {
          params.total(success.totalElements);
          return success.content;
        });
      }
    });


    vm.calendar = {
      opened: false
    };

    vm.openCalendar = function () {
      vm.calendar.opened = true;
    };

    vm.search = function () {
      if (vm.filters.date) {
        delete vm.searchParams.dateBefore;
        delete vm.searchParams.dateAfter;
        // var formattedDate = new Date(vm.filters.date.getTime() + vm.filters.date.getTimezoneOffset() * 60000).toJSON().substring(0, vm.filters.date.toJSON().indexOf('T'));
        var formattedDate = vm.filters.date.toJSON().substring(0, vm.filters.date.toJSON().indexOf('T'));
        if (vm.filters.ba === 'before') {
          vm.searchParams.dateBefore = formattedDate;
        } else {
          vm.searchParams.dateAfter = formattedDate;
        }
      }
      vm.tableParams.reload();
    };

    vm.clearSearch = function () {
      vm.searchParams = {};
      delete vm.filters.date;
      vm.tableParams.reload();
    };

  }

})();