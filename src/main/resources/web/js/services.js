'use strict';

/* Services */

angular.module('WormServices', ['ngResource'])
  .factory('Worm', ['$resource',
    function($resource){
      return $resource('beers/:beerId.json', {}, {
        query: {method:'GET', params:{beerId:'beers'}, isArray:true}
      });
    }]);
