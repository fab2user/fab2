(function() {
  'use strict';

  angular
    .module('hub')
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
        vm.title = $translate.instant('status.notification.title');
        vm.spin = true;
        vm.message = $translate.instant('status.notification.message.started');
        $scope.$on(EVENT.XML_EXPORT, function($event, statusObj) {
          vm.visible = true;
          vm.country = statusObj.countryName;
          vm.status = statusObj.status;
          vm.message =
            statusObj.message ||
            $translate.instant('status.notification.message.started');
          vm.style = 'notification-processing';
          switch (vm.status) {
            case STATUS.OK:
              vm.style = 'notification-success';
              vm.spin = false;
              break;
            case STATUS.ERROR:
              vm.style = 'notification-failure';
              vm.spin = false;
          }
        });

        vm.hide = function() {
          vm.visible = false;
        };
      }
    };
  }
})();
