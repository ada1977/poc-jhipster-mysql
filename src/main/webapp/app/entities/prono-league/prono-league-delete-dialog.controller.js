(function() {
    'use strict';

    angular
        .module('adaApp')
        .controller('PronoLeagueDeleteController',PronoLeagueDeleteController);

    PronoLeagueDeleteController.$inject = ['$uibModalInstance', 'entity', 'PronoLeague'];

    function PronoLeagueDeleteController($uibModalInstance, entity, PronoLeague) {
        var vm = this;

        vm.pronoLeague = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PronoLeague.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
