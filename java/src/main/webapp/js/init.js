/*
 * import/include a .js file into a .js file
 */
function inc(filename){
   //this line can be used instead of all scripts below
   document.write("<script type='text/javascript' src=" + filename + "></script>");
   
   /*var script = document.createElement('script');
   script.src = filename;
   script.type = 'text/javascript';
   script.defer = true;
   //script.id = 'scriptID'; // This will help us in referencing the object later for removal
   
   // Insert the created object to the html head element
   var head = document.getElementsByTagName('head').item(0);
   head.appendChild(script);*/
}

inc('/FuzzyLogic/js/comboBoxCarregarExemplo.js');
inc('/FuzzyLogic/js/RequestingJson.js');
inc('/FuzzyLogic/js/dataDispatcher.js');
inc('/FuzzyLogic/js/grafico.js');




