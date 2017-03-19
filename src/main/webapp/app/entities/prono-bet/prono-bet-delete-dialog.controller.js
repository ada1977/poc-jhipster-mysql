(function() {
    'use strict';

    angular
        .module('adaApp')
        .controller('PronoBetDeleteController',PronoBetDeleteController);

    PronoBetDeleteController.$inject = ['$uibModalInstance', 'entity', 'PronoBet'];

    function PronoBetDeleteController($uibModalInstance, entity, PronoBet) {
        var vm = this;

        vm.pronoBet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PronoBet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
