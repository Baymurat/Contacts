(function (global) {
    "use strict";

    /** modal function
     *
     *
     */
    global.Modal = function (elt, userSettings, table) {
        if (!elt) throw "please, specify element";

        var defaults = {
            closeOnEscape: true,
            closeOnClickOutside: true
        };

        this.elt = elt;
        this.table = table;
        this.hide();

        this.settings = userSettings ? merge(defaults, userSettings) : defaults;
        this.__subscribe();
    };

    global.Modal.prototype.__subscribe = function () {
        var widget = this;
        if (this.settings.closeOnEscape) {
            document.addEventListener("keyup", function (e) {
                if (e.code === "Escape") {
                    widget.hide();
                }
            });
        }

        if (this.settings.closeOnClickOutside) {
            document.addEventListener("click", function (e) {
                var target = e.target;
                if (target && target.id === "fade") {
                    widget.hide();
                }
            })
        }

        this.elt.querySelectorAll("[data-role = close]").forEach(function (button) {
            button.addEventListener("click", function () {
                widget.hide();
            });
        })

        this.elt.querySelector("#cancel-button").addEventListener("click", function () {
            widget.hide();
        });

        this.table.addEventListener('click', function(e) {
            var target = e.target;
            var parentNode = target.parentNode;
            if(target && target.tagName == 'TD') {
                if(!parentNode.classList.contains('selected')) {
                    var selectedRow = widget.table.querySelector('.selected');
                    if(selectedRow) {
                        selectedRow.classList.toggle('selected');
                    }
                }
                parentNode.classList.toggle('selected');
            }
        })
    };

    global.Modal.prototype.show = function () {
        showFade();
        this.elt.style.display = "block";
    };

    global.Modal.prototype.hide = function () {
        hideFade();
        this.elt.style.display = "none";

        var inputs = this.elt.querySelectorAll('input');
        for (var i = 0; i < inputs.length; i++) {
            inputs[i].value = "";
        }
    };

    //CONTINUE FROM HERE
    global.Modal.prototype.edit = function(callback) {
        var widget = this;
        var selectedRow = this.table.querySelector('.selected');
        
        if(selectedRow) {
            widget.show();
            callback(selectedRow);
        }
    }

    global.Modal.prototype.delete = function(callback) {
        var widget = this;
        var selectedRow = this.table.querySelector('.selected');
        
        if(selectedRow) {
            callback(selectedRow);
        }
    }

    global.Modal.prototype.bindAddFuncAndButton = function(callback) {
        var widget = this;
        widget.elt.querySelector('#accept-button').addEventListener('click', function() {
            callback(widget.elt, widget.table);
            widget.hide();
        });
    }

    function showFade() {
        var box = document.createElement("div");
        box.classList.add("fade");
        box.setAttribute("id", "fade");
        document.body.appendChild(box);
    }

    function hideFade() {
        var box = document.getElementById("fade");
        if (box) {
            box.parentNode.removeChild(box);
        }
    }

    function merge(defaults, settings) {
        var mergedObject = {};
        for (var property in defaults) {
            if (defaults.hasOwnProperty(property)) {
                mergedObject[property] = settings[property] ? settings[property] : defaults[property];
            }
        }

        return mergedObject;
    }

}(window));