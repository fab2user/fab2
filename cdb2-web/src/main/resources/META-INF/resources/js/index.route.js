(function () {
  'use strict';

  angular
    .module('cdb2')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
      .state('root', {
        abstract: true,
        views: {
          'navbar': {
            templateUrl: '/js/root/navbar.html',
            controller: 'NavBarController as navCtrl'
          },
          'footer': {
            templateUrl: '/js/root/footer.html',
            controller: 'FooterController as footerCtrl'
          }
        }
      })
       .state('root.home', {
         url: '/',
         views: {
           'content@': {
             templateUrl: '/js/home/home.html',
             controller: 'HomeController',
             controllerAs: 'homeCtrl'
           }
         }
       })
      .state('root.area', {
        url: '/area',
        views: {
          'content@': {
            templateUrl: '/js/geo-area/area.html',
            controller: 'GeoAreaController',
            controllerAs: 'areaCtrl'
          }
        }
      })
      .state('root.municipality', {
        url: '/municipality',
        views: {
          'content@': {
            templateUrl: '/js/municipality/municipality.html',
            controller: 'MunicipalityController',
            controllerAs: 'municipalityCtrl'
          }
        }
      });

    $urlRouterProvider.otherwise('/');
  }

})();
