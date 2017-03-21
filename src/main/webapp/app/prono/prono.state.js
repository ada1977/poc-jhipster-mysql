(function() {
    'use strict';

    angular
        .module('adaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('prono', {
            parent: 'app',
            url: '/prono',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'prono.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/prono/prono.html',
                    controller: 'PronoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('prono');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
