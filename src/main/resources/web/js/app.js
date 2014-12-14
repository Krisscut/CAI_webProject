'use strict';

/* App Module */

var wormBuyer = angular.module('WormBuyer', [
  'ngRoute'/*,
  'WormsControllers',
  'WormFilters',
  'WormServices',
  'WormAnimations'*/
]);


wormBuyer.controller("wormtruc", ["$scope", "$http", function($scope,$http){
	$scope.worms = [];
	$scope.equipments =[];
	$scope.panier =[];
	
	$scope.addTo = function(object){
	$scope.panier.push(object);
	
	}
	
	$scope.getWormData = function(){
		$http.get("/api/worms/getAll").
		success(function(data, status){
			$scope.worms = data.result;
			$scope.equipments = [];
			$scope.bonuses = [];
		}).
		error(
		function(data, status){
			alert("erreur!")
		}
		);
	}
	
	$scope.getEquipmentData = function(){
		$http.get("/api/equipments/getAll").
		success(function(data, status){
		if (data.status !="Unknow Command"){
			$scope.equipments = data.result;
			$scope.worms = [];
			$scope.bonuses = [];
		}else{
			alert(data.status);
		}
		}).
		error(
		function(data, status){
			alert("erreur!")
		}
		);
	}
		$scope.getBonusData = function(){
		$http.get("/api/bonuses/getAll").
		success(function(data, status){
		if (data.status !="Unknow Command"){
			$scope.bonuses = data.result;
			$scope.worms = [];
			$scope.equipments = [];
		}else{
			alert(data.status);
		}
		}).
		error(
		function(data, status){
			alert("erreur!")
		}
		);
	}
	
}]);
