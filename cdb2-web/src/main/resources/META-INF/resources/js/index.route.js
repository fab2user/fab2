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
          'head': {
            templateUrl: '/js/root/head.html',
            controller: 'HeadController as headCtrl'
          },
          'navbar@': {
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
      })
      .state('root.bailiff', {
        url: '/bailiff',
        views: {
          'content@': {
            templateUrl: '/js/bailiff/bailiff.html',
            controller: 'BailiffController',
            controllerAs: 'bailiffCtrl'
          }
        }
      })
      .state('root.reference', {
        url: '/reference',
        views: {
          'content@': {
            templateUrl: '/js/reference/reference.html',
            controller: 'ReferenceController',
            controllerAs: 'refCtrl'
          }
        }
      })
      .state('root.login', {
        url: '/login',
        views: {
          'content@': {
            templateUrl: '/js/common/login.html',
            controller: 'LoginController',
            controllerAs: 'loginCtrl'
          }
        },
        resolve: { // Redirects user to where he came from before login state 
          PreviousState: ['$state', function ($state) {
            var currentStateData = {
              name: $state.current.name,
              params: $state.params
            };
            return currentStateData;
          }]
        }
      });

    $urlRouterProvider.otherwise('/bailiff');
  }

})();