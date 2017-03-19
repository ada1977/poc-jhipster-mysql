(function() {
    'use strict';

    angular
        .module('adaApp')
        .controller('PronoLeagueDetailController', PronoLeagueDetailController);

    PronoLeagueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PronoLeague', 'PronoGame'];

    function PronoLeagueDetailController($scope, $rootScope, $stateParams, previousState, entity, PronoLeague, PronoGame) {
        var vm = this;

        vm.pronoLeague = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('adaApp:pronoLeagueUpdate', function(event, result) {
            vm.pronoLeague = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
