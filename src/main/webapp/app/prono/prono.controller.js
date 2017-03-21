(function() {
    'use strict';

    angular
        .module('adaApp')
        .controller('PronoController', PronoController);

    PronoController.$inject = ['Prono'];

    function PronoController(Prono) {

        var vm = this;

        vm.pronos = [];

        loadAll();

        function loadAll() {
            Prono.query(function(result) {
                vm.pronos = result;
                //vm.searchQuery = null;
            });
        }
    }
})();
