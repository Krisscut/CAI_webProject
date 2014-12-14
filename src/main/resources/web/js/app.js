'use strict';

/* App Module */

var wormBuyer = angular.module('WormBuyer', [
  'ngRoute',
  'WormsControllers',
  'WormFilters',
  'WormServices',
  'WormAnimations'
]);

angularBeer.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/beers', {
        templateUrl: 'partials/beer-list.html',
        controller: 'BeerListCtrl'
      }).
      when('/beers/:beerId', {
        templateUrl: 'partials/beer-detail.html',
        controller: 'BeerDetailCtrl'
      }).
      otherwise({
        redirectTo: '/beers'
      });
  }]);