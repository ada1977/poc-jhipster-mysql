(function() {
    'use strict';

    angular
        .module('adaApp')
        .controller('PronoGameDeleteController',PronoGameDeleteController);

    PronoGameDeleteController.$inject = ['$uibModalInstance', 'entity', 'PronoGame'];

    function PronoGameDeleteController($uibModalInstance, entity, PronoGame) {
        var vm = this;

        vm.pronoGame = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PronoGame.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
