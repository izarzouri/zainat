/*
* WCM Sitefactory Mediator
* Used in IA component context
*
* author i30897
*
*/

Mediator = new function () {
    var debugMode = false;
    var registeredIa = new Array();

    // store if IA post parameter exist
    this.init = function () {
        if (typeof console == "undefined") {
            debugMode = false;
        } else {
            this.logInfo("DeviceFeatures - debug mode enabled");
        }

        if (typeof (sfIaParam) != "undefined") {
            this.store(sfIaParam.topic, sfIaParam);
        }
		
        this.subscribeToTopic("target.ia.**", function (topic, data) {
            Mediator.logInfo("Mediator - Target callback start");
            if (topic && typeof (sfIaTargetList) != "undefined" && sfIaTargetList[sfSiteId][topic]) {
                Mediator.logInfo("Mediator - Target found for topic: " + topic);
                Mediator.sendData(topic, data, sfIaTargetList[sfSiteId][topic]);
            } else {
                Mediator.logInfo("Mediator - Target not found for topic: " + topic);
            }
        });
    }
	
    // subscribe to topic
    this.subscribeToTopic = function (topicName, callback) {
        this.logInfo("Mediator - subscribe to topic: " + topicName);
        return window.PageBus.subscribe(topicName, null, callback, null);
    }
	
    // subscribe to array of topic
    this.subscribeToListOfTopic = function (topicNames, callback) {
        for (var index in topicNames) {
            this.logInfo("Mediator - subscribe to topic: " + topicName[index]);
            window.PageBus.subscribe(topicNames[index], null, callback, null);
        }
    }
	
    // store parameter
    this.store = function (topicName, parameter) {
        this.logInfo("Mediator - store parameter: topic=" + topicName + " parameter=" + parameter);
        window.PageBus.subscribe(topicName, function (topic, message, data) {
            return;
        }, null, {
            PageBus: {
                cache: true
            }
        });
        window.PageBus.store(topicName, parameter);
    }
	
	// check if a topic stored
	this.checkQuery = function (topicName) {
		if (typeof window.PageBus.query(topicName)[0] != 'undefined'){
			return true;
		} else {
			return false;
		}
	}
	
    // send data to server
    this.sendPostData = function (topic, data, url) {
        this.logInfo("Mediator - send POST data: topic=" + topic + " data=" + data + " target=" + url);
        var form = document.createElement("form");
        form.setAttribute("method", "POST");
        form.setAttribute("action", url);
        form.setAttribute('enctype', 'application/json');

        var jsonData;
        try {
            var parseData = JSON.parse(data);
            parseData.topic = topic;
            jsonData = JSON.stringify(parseData);
        } catch (exception) {
            data.topic = topic;
            jsonData = JSON.stringify(data);
        }

        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "sfIaParam");
        hiddenField.setAttribute("value", jsonData);

        form.appendChild(hiddenField);
        document.body.appendChild(form);
        form.submit();
    }
	
    // send data to the target
    this.sendData = function (topic, data, url) {
        this.logInfo("Mediator - send data: topic=" + topic + " data=" + data + " target=" + url);
        var iaId = this.getIaIdFromTopic(topic);
        if (iaId && (typeof registeredIa[iaId] != "undefined")) {
            this.logInfo("Mediator - IA " + iaId + " registered in the page, start process");
            if (registeredIa[iaId] === true) {
                this.logInfo("Mediator - IA " + iaId + " must loaded");
                this.store(topic, data);
                registeredIa[iaId] = false;
                window.PageBus.publish("mediator.load.ia." + iaId);
            } else {
                this.logInfo("Mediator - IA " + iaId + " already loaded");
            }
        } else if (url) {
            this.logInfo("Mediator - IA " + iaId + " not registered in the page, go to " + url);
            this.sendPostData(topic, data, url);
        }
    }
	
    // regiter IA
    this.registerIa = function (iaId, status) {
        registeredIa[iaId] = status;
    }
	
    // get IA id from topic 
    this.getIaIdFromTopic = function (topic) {
        var iaId = topic.split(".ia.")[1];
        if (iaId.indexOf(".") != -1) {
            return iaId.substring(0, iaId.indexOf("."));
        }
        return iaId;
    }
	
    // enable debug mode
    this.enableDebugMode = function () {
        debugMode = true;
    }
	
    // log info to the console
    this.logInfo = function (messageLog) {
        if (debugMode === true) {
            console.log(messageLog);
        }
    }

};

Mediator.init();