(function () {
  'use strict';

  angular.module('cdb2')
    .directive('statusDirective', statusDirective);
    statusDirective.$inject = ['$log', '$rootScope', '$translate', 'toastr', 'EVENT', 'STATUS'];

  function statusDirective($log, $rootScope, $translate, toastr, EVENT, STATUS) {
    return {
      restrict: 'E',
      controllerAs: 'statusCtrl',
      templateUrl: '/js/common/status/status.html',
      controller: function () {
        var vm = this;
        vm.visible = false;
        $rootScope.$on(EVENT.XML_IMPORT, function($event, statusObj){
          var status = statusObj.status;
          switch (status){
            case STATUS.IN_PROGRESS:
            vm.visible = true;
            vm.message = $translate.instant('import.bailiff.inprogress');
            vm.alertStyle = 'alert-warning';
            break;
            case STATUS.OK:
            vm.visible = false;
            toastr.success($translate.instant('import.bailiff.ok'));
            break;
            case STATUS.ERROR:
            vm.visible = false;
            toastr.error(statusObj.message);
          }
        });
      }
  };
}
})();