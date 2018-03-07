(function () {
  'use strict';

  angular
    .module('hub')
    .controller('StatusController', StatusController);

  StatusController.$inject = ['$log', 'NgTableParams', 'lodash', 'StatusAPIService'];

  function StatusController($log, NgTableParams, lodash, StatusAPIService) {
    var vm = this;

    vm.filters = {
      ba: 'after'
    };

    vm.searchParams = {};

    vm.tableParams = new NgTableParams({
      count: 10,
      page: 1
    }, {
      getData: function (params) {
        var sort = function (params) {
          var sortParam = params.sorting();
          if (Object.keys(sortParam).length > 0) {
            var k = Object.keys(sortParam)[0];
            return k + ',' + sortParam[k];
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

    vm.search = function(){
      if(vm.filters.date){
        delete vm.searchParams.dateBefore;
        delete vm.searchParams.dateAfter;
        if(vm.filters.ba === 'before'){
          vm.searchParams.dateBefore =  vm.filters.date.toJSON().substring(0,vm.filters.date.toJSON().indexOf('T'));
          // delete vm.searchParams.date;
        }else{
          vm.searchParams.dateAfter =  vm.filters.date.toJSON().substring(0,vm.filters.date.toJSON().indexOf('T'));
          // delete vm.searchParams.date;
        }
      }
      vm.tableParams.reload();
    };





  }

})();