/*
*	BroadCasting Message (USING broadcast api for chrome or localstorage)
*/
function makeBrodCast(){
                var result = document.getElementById("txt").value;
                const channel = new BroadcastChannel('channel-name');
                channel.postMessage(result);
            }

function localStorageBroadCast(){
                var result = document.getElementById("txt").value;
                localStorage.setItem('message', result);
            }

/*
*   Reciving the message (USING broadcast api for chrome or localstorage)
*/
const channel = new BroadcastChannel('channel-name');
            channel.onmessage = function(message) {
                // do something with the message received from A.com
                document.getElementById("txt").value = message.data; 
                channel.close();
           };

window.onstorage = function(e) {
            const message = e.newValue; // previous value at e.oldValue
            document.getElementById("txt").value = message;
           };
			
