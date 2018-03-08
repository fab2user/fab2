(function () {
  'use strict';

  angular
    .module('hub')
    .config(routerConfig);
  routerConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

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
      .state('root.country', {
        url: '/country',
        views: {
          'content@': {
            templateUrl: '/js/country/country.html',
            controller: 'CountryController',
            controllerAs: 'countryCtrl'
          }
        }
      })
      .state('root.status', {
        url: '/status',
        views: {
          'content@': {
            templateUrl: '/js/status/status.html',
            controller: 'StatusController',
            controllerAs: 'statusCtrl'
          }
        },
        resolve: {
          countryList: ['CountryAPIService', function(CountryAPIService){
            return CountryAPIService.getRefs();
          }]
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

    $urlRouterProvider.otherwise('/');
  }

})();