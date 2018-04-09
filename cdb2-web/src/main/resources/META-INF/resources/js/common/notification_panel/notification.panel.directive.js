(function() {
  'use strict';

  angular
    .module('cdb2')
    .directive('notificationPanelDirective', notificationPanelDirective);
  notificationPanelDirective.$inject = [
    '$log',
    '$translate',
    'EVENT',
    'STATUS'
  ];

  function notificationPanelDirective($log, $translate, EVENT, STATUS) {
    return {
      restrict: 'E',
      controllerAs: 'notifCtrl',
      templateUrl: '/js/common/notification_panel/notification.panel.html',
      controller: function($scope) {
        var vm = this;
        vm.visible = false;
        vm.spin = true;
        vm.message = $translate.instant('monitoring.message.started');

        $scope.$on(EVENT.GEONAME_IMPORT, function($event, statusObj) {
          vm.processEvent(statusObj);
          vm.title = $translate.instant('monitoring.geonameimport.title');
        });

        $scope.$on(EVENT.BAILIFF_IMPORT, function($event, statusObj) {
          vm.processEvent(statusObj);
          vm.title = $translate.instant('monitoring.bailiffimport.title');
        });

        $scope.$on(EVENT.BAILIFF_EXPORT, function($event, statusObj) {
          vm.processEvent(statusObj);
          vm.title = $translate.instant('monitoring.bailiffexport.title');
        });

        vm.hide = function() {
          vm.visible = false;
        };

        vm.processEvent = function(statusObj) {
          vm.visible = true;
          vm.status = statusObj.status;
          vm.style = 'notification-processing';
          switch (vm.status) {
            case STATUS.OK:
              vm.style = 'notification-success';
              vm.spin = false;
              vm.message = $translate.instant('monitoring.message.success');
              break;
            case STATUS.ERROR:
              vm.style = 'notification-failure';
              vm.spin = false;
              vm.message = statusObj.message;
              break;
            default:
              vm.message = $translate.instant('monitoring.message.processing');
          }
        };
      }
    };
  }
})();
