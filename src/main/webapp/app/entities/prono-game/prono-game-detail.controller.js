(function() {
    'use strict';

    angular
        .module('adaApp')
        .controller('PronoGameDetailController', PronoGameDetailController);

    PronoGameDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PronoGame', 'PronoLeague', 'PronoBet'];

    function PronoGameDetailController($scope, $rootScope, $stateParams, previousState, entity, PronoGame, PronoLeague, PronoBet) {
        var vm = this;

        vm.pronoGame = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('adaApp:pronoGameUpdate', function(event, result) {
            vm.pronoGame = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
