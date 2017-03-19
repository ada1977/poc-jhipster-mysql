(function() {
    'use strict';

    angular
        .module('adaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('prono-league', {
            parent: 'entity',
            url: '/prono-league?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'adaApp.pronoLeague.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prono-league/prono-leagues.html',
                    controller: 'PronoLeagueController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pronoLeague');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('prono-league-detail', {
            parent: 'prono-league',
            url: '/prono-league/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'adaApp.pronoLeague.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prono-league/prono-league-detail.html',
                    controller: 'PronoLeagueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pronoLeague');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PronoLeague', function($stateParams, PronoLeague) {
                    return PronoLeague.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'prono-league',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('prono-league-detail.edit', {
            parent: 'prono-league-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-league/prono-league-dialog.html',
                    controller: 'PronoLeagueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PronoLeague', function(PronoLeague) {
                            return PronoLeague.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prono-league.new', {
            parent: 'prono-league',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-league/prono-league-dialog.html',
                    controller: 'PronoLeagueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                leagueName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('prono-league', null, { reload: 'prono-league' });
                }, function() {
                    $state.go('prono-league');
                });
            }]
        })
        .state('prono-league.edit', {
            parent: 'prono-league',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-league/prono-league-dialog.html',
                    controller: 'PronoLeagueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PronoLeague', function(PronoLeague) {
                            return PronoLeague.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prono-league', null, { reload: 'prono-league' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prono-league.delete', {
            parent: 'prono-league',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prono-league/prono-league-delete-dialog.html',
                    controller: 'PronoLeagueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PronoLeague', function(PronoLeague) {
                            return PronoLeague.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prono-league', null, { reload: 'prono-league' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
