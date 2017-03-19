(function() {
    'use strict';

    angular
        .module('adaApp')
        .controller('PronoBetDetailController', PronoBetDetailController);

    PronoBetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PronoBet', 'PronoGame'];

    function PronoBetDetailController($scope, $rootScope, $stateParams, previousState, entity, PronoBet, PronoGame) {
        var vm = this;

        vm.pronoBet = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('adaApp:pronoBetUpdate', function(event, result) {
            vm.pronoBet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
