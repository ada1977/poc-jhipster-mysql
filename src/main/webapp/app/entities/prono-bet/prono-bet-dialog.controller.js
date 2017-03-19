(function() {
    'use strict';

    angular
        .module('adaApp')
        .controller('PronoBetDialogController', PronoBetDialogController);

    PronoBetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PronoBet', 'PronoGame'];

    function PronoBetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PronoBet, PronoGame) {
        var vm = this;

        vm.pronoBet = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pronogames = PronoGame.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pronoBet.id !== null) {
                PronoBet.update(vm.pronoBet, onSaveSuccess, onSaveError);
            } else {
                PronoBet.save(vm.pronoBet, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('adaApp:pronoBetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
