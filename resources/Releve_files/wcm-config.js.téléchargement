(function() {
    'use strict';

    // Configure variables


    var wcm = window.wcm = {
            ready: addPostConfigHandler,
            bootstrap: bootstrap,
            configure: configure,
            showVersion: showVersion,
            moduleConfig: configureModules,
            unload: unloadContext
        },
        postConfigHandlers = [],
        wcmCtxName = 'wcm',
        bnppNamespace = 'bnpp', /* common namespace to use by every entity, don't modify it */
        useBnppNamespace = false,/* don't modify this variable, it will be by config */
        moduleConfig = {},
        wcmCtx,
        requr = window.require;

    /**
     * The main RequireJS configuration for WCM
     * - BaseURL is determined based on the script tag of wcm-config.js
     * - Maps are used to ensure that rivets/backbone/underscore get special treatment.
     * @returns {*}
     */
    function requireConfig() {
        // Configure RequireJS
        var ctx = requr.config({
            context: wcmCtxName,
            baseUrl: /(.*?)\/wcm-config/.exec($('script[src$="wcm-config.js"]').attr('src'))[1],
            paths: {
                sf: (useBnppNamespace ? 'sf-bnpp' : 'sf')
            },
            bundles: {
                sf: [
                    'backbone-computed-fields',
                    'backbone',
                    'css',
                    'normalize',
                    'rivets',
                    'text',
                    'less', // Only to be used on development. In prod, LESS files should be precompiled.
                    'underscore',
                    'sf/bootstrap',
                    'sf/backbone-private',
                    'sf/underscore-private',
                    'sf/rivets-private',
                    'sf/browser-features'
                ]
            },
            map: {
                '*': {
                    'backbone': 'sf/backbone-private',
                    'underscore': 'sf/underscore-private',
                    'rivets': 'sf/rivets-private'
                },
                'sf/backbone-private': { 'backbone': 'backbone' },
                'backbone-computed-fields': { 'backbone': 'backbone' },
                'sf/underscore-private': { 'underscore': 'underscore' },
                'sf/rivets-private': { 'rivets': 'rivets' }
            },
            config: moduleConfig,
            callback: function() {
                postConfigHandlers.forEach(function(handler) {
                    ctx(handler.deps, handler.callback);
                });
            }
        });

        // Set the elements available in the page in the context
        set(wcmCtxName, 'pagebus', window.top.PageBus);
        set(wcmCtxName, 'jquery', window.$);
        set(wcmCtxName, 'modernizr', window.Modernizr);
        set(wcmCtxName, 'global-require', requr);

        return ctx;
    }

    // Execute requireConfig only once
    function configure() {
        if (!wcmCtx) wcmCtx = requireConfig();
        return wcmCtx;
    }

    // Bootstrap global fn
    function bootstrap(cfg) {
        if (!wcmCtx) wcmCtx = requireConfig();
        wcmCtx(['sf/bootstrap','sf/browser-features'], function(bs) {
            bs.load(cfg);
        });
    }

    function unloadContext(contextName){
        wcmCtx(['sf/bootstrap'], function(bs) {
            bs.unload(contextName);
        });
    }

    // AddPostConfigHandler global fn
    function addPostConfigHandler(deps, callback) {
        postConfigHandlers.push({deps: deps, callback: callback});
    }

    // Context manipulation function
    function set(context, id, value) {
        requr.s.contexts[context].defined[id] = value;
    }

    function showVersion() {
        if (!window.console) return;
        if (!wcmCtx) wcmCtx = requireConfig();
        wcmCtx(['sf'], function(sf) {
            window.console.info(sf.version.toString());
        });
    }

    /**
     * Configure any SF modules
     *
     * @param cfg {*} Configuration object containing
     *          map (optional): The global redirect configuration map
     *          params (optional): The pagebus initialization parameters
     *          paramName (optional, default: sfparam): The parameter name for the POST redirects
     *          page (optional, default: window.top.location.pathname): A string representing current page.
     *          paramVersion (optional, default: 2): A string representing the version of mediator/messaging param used
     */
    function configureModules(cfg) {
        var mediator2Module = 'sf/mediator2',
            messagingModule = 'sf/messaging',
            errLogModule = 'sf/errlog',
            csrfModule = 'sf/csrf';

        addToConfig(mediator2Module, 'map', cfg);
        addToConfig(mediator2Module, 'paramName', cfg);
        addToConfig(mediator2Module, 'page', cfg);
        addToConfig(mediator2Module, 'crossPageMessageStrategy', cfg);
        addToConfig(mediator2Module, 'defaultTargetFrame', cfg);
        addToConfig(messagingModule, 'paramVersion', cfg);
        addToConfig(messagingModule, 'params', cfg);
        addToConfig(csrfModule, 'secure', cfg);
        addToConfig(errLogModule, 'errorUrl', cfg);

        if (!!cfg['useBnppNamespace']){
            if (window[bnppNamespace] !== undefined && window[bnppNamespace].require !== undefined){
                useBnppNamespace = true;
                requr = window[bnppNamespace].require;      /* bnpp.require */
            }else{
                window.console.warn('Please include a require.js version that uses the bnpp namespace!');
            }
        }
    }

    /**
     * Adds a configuration element from a configuration object to a module configuration,
     * if the property is defined.
     *
     * @param moduleName The module whose config to modify
     * @param property The property to set
     * @param configuration The configuration that may contain the property
     */
    function addToConfig(moduleName, property, configuration) {
        // If the property does not exist in the configuration, return
        if (configuration[property] === undefined) return;

        // Returns a config object for a module
        function getConfig(moduleName) {
            moduleConfig[moduleName] = moduleConfig[moduleName] || {};
            return moduleConfig[moduleName];
        }

        // Store the property inside the config for the module.
        getConfig(moduleName)[property] = configuration[property];
    }

    function getCookies() {
        var cookies = document.cookie.split( ';' );
        var list = {} ;
        for (var i = 0; i < cookies.length; i++){
            var cv = cookies[i].split('=');
            var name = cv[0].replace(/\s+/g, "").replace(/\s+$/g,"") ;
            list[name] =  cv[1] ;
        }
       return (list) ;
    }


})();