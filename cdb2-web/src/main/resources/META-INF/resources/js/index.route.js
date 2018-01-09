(function () {
  'use strict';

  angular
    .module('cdb2')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
      .state('site', {
        abstract: true,
        views: {
          'navbar@': {
            templateUrl: '/js/site/site.html',
            controller: 'SiteController as siteCtrl'
          }
        }
      })
      .state('home', {
        parent: 'site',
        url: '/',
        views: {
          'content@': {
            templateUrl: '/js/main/main.html',
            controller: 'MainController',
            controllerAs: 'main'
          }
        }
      })
      .state('area', {
        parent: 'site',
        url: '/area',
        views: {
          'content@': {
            templateUrl: '/js/geo-area/area.html',
            controller: 'GeoAreaController',
            controllerAs: 'area'
          }
        }
      });

    $urlRouterProvider.otherwise('/');
  }

})();
