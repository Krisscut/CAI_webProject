'use strict';

/* Controllers */

angular
  .module('WormControllers', [])
  .controller('WormListCtrl', ['$scope', 'Worm', function($scope, Worm) {

    $scope.beers = Beer.query();

    $scope.orderProp = 'alcohol';
  }])
  .controller('WormDetailCtrl', ['$scope', '$routeParams', 'Beer', function($scope, $routeParams, Beer) {

    $scope.beer = Beer.get({beerId: $routeParams.beerId}, function(beer) {
      $scope.mainImg = beer.img;
    });

    $scope.setImage = function(img) {
      $scope.mainImg = img;
    }
  }]);

