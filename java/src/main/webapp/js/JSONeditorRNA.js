/*
jsonEditor 1.02
copyright 2007-2009 Thomas Frank

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.
*/

JSONeditorRNA={
	start:function(treeDivName,formDivName,json,showExamples){
		if(this.examples.length<6){
			var e=this.treeBuilderRNA.JSONstring.make(this)
			eval("this.examples[5]={JSONeditorRNA:"+e+"}")
		}
		this.treeDivName=treeDivName
		var t=this.treeBuilderRNA, $=t.$
		treeBuilderRNA=t
		var s=$(treeDivName).style
		var f=$(formDivName)
		var fs=f.style
		f.innerHTML=this.formHTML
		if(!showExamples){$('jExamplesRNA').style.display="none"}
		fs.fontSize=s.fontSize="11px"
		fs.fontFamily=s.fontFamily="Verdana,Arial,Helvetica,sans-serif"
		var e=f.getElementsByTagName("*")
		for(var i=0;i<e.length;i++){
			var s=e[i].style
			if(s){
				s.fontSize="11px"
				s.fontFamily="Verdana,Arial,Helvetica,sans-serif"
			}
		}
		json=json||{}
		t.JSONbuild(treeDivName,json)
	},
	loadExample:function(x){
		treeBuilderRNA.hasRunJSONbuildOnce=false
		treeBuilderRNA.JSONbuild(this.treeDivName,this.examples[x/1])
	},
		
	formHTML:"<form name=\"jsoninputRNA\" onsubmit=\"return treeBuilderRNA.jsonChange(this)\"><div id=\"jExamplesRNA\"></div>\n<input onclick=\"listar_estrutura_de_diretorios_rna()\" type=\"button\" class=\"btn\" value=\"Listar estrutura de diretórios\"><br><br>\nCaminho do Item Selecionado:<br><input name=\"jlabelRNA\" type=\"text\" value=\"\" size=\"60\" style=\"width:400px\"><br><br>\nValor: <br><textarea id=\"idEditorJsonRNA\" name=\"jvalueRNA\" rows=\"10\" cols=\"50\" style=\"width:400px\"></textarea><br><br>\nTipo de Dado: <select onchange=\"treeBuilderRNA.changeJsonDataType(this.value,this.parentNode)\" name=\"jtypeRNA\">\n<option value=\"object\">object</option>\n<option value=\"array\">array</option>\n<option value=\"function\">function</option>\n<option value=\"string\">string</option>\n<option value=\"number\">number</option>\n<option value=\"boolean\">boolean</option>\n<option value=\"null\">null</option>\n<option value=\"undefined\">undefined</option>\n</select>&nbsp;&nbsp;&nbsp;&nbsp;\n<input name=\"orgjlabelRNA\" type=\"hidden\" value=\"\" size=\"50\" style=\"width:300px\">\n<input onfocus=\"this.blur()\" type=\"submit\" class=\"btn\" value=\"Salvar\">&nbsp;\n<br><br>\n<div id=\"jformMessageRNA\"></div>\n</form>",
	examples:[{},{},{},]
	
}


/*
treeBuilderRNA v 1.00 + a lot of json stuff added...
copyright 2007 Thomas Frank
*/
JSONeditorRNA.treeBuilderRNA={
	stateMem:{},
	images:{
		folderNode:'',
		folderNodeOpen:'',
		folderNodeLast:'',
		folderNodeOpenLast:'',
		docNode:'',
		docNodeLast:'',
		folder:'',
		folderOpen:'',
		doc:'',
		vertLine:'',
		folderNodeFirst:'',
		folderNodeOpenFirst:'',
		folderNodeLastFirst:'',
		folderNodeOpenLastFirst:'',
		path:'js/treeBuilderImages/',
		nodeWidth:16
	},
	$:function(x){return document.getElementById(x)},
	preParse:function(x){
		var x=x.innerHTML.split("\n");
		var d=[];
		for(var i=0;i<x.length;i++){
			if(x[i]){
				var y=x[i].split("\t");
				var l=0;while(!y[l]){l++};
				var la=y[l]?y[l]:'';l++;
				var t=y[l]?y[l]:'';
				d.push({level:l,label:la,todo:t});
			}
		};
		return d
	},
	isArray:function(x){
		return x.constructor==Array
	},
	jSyncTree:function(x){
		var d=this.$(this.baseDiv).getElementsByTagName('div')
		for(var i=0;i<d.length;i++){
			
			treeBuilderRNA.maniClick="giveItBack"
			var p=d[i].onclick()
			if(p==x){
				var t=d[i]
				treeBuilderRNA.maniClick="selectIt"
				t.onclick()
				t=t.parentNode
				while(t.id!=this.baseDiv){if(t.style){this.openAndClose(t.id,"open")};t=t.parentNode}
			}
		}
		treeBuilderRNA.maniClick=false
	},
	jsonResponder:function(x){
		this.jTypeChanged=false
		treeBuilderRNA.jSyncTree(x)
		var t=treeBuilderRNA
		eval("var a=treeBuilderRNA."+x)
		eval("var ap=treeBuilderRNA."+treeBuilderRNA.jsonParent(x))
		var b=t.JSONstring.make(a)
		var t=(a && treeBuilderRNA.isArray(a))?"array":typeof a
		var tp=(ap && treeBuilderRNA.isArray(ap))?"array":typeof ap
		if(a===null){t="null"}
		var f=document.forms.jsoninputRNA
		if(t=="string"){eval("b="+b)}
		f.jlabelRNA.value=x
		f.orgjlabelRNA.value=x
		f.jvalueRNA.value=b
		f.jtypeRNA.value=t
		f.jlabelRNA.disabled=f.jlabelRNA.value=="json"
		f.jtypeRNA.disabled=f.jlabelRNA.disabled
	},
	jsonParent:function(x){          
		// inmproved thanks to \x000
		if(x=="json"){return "treeBuilderRNA"} 
		if (x.charAt(x.length-1)==']') {return x.substring(0,x.lastIndexOf('['))}                  
		return x.substring(0,x.lastIndexOf('.'))     
	},	
	jsonChild:function(el1){
		var p=this.jsonParent(el1)
		el1=el1.split(p).join("")
		if(el1.charAt(0)=="."){el1=el1.substring(1)}
		if(el1.charAt(0)=="["){el1=el1.substring(2,el1.length-2)}
		return el1
	},
	jsonRemove:function(f){
		this.jsonChange(f,true)
	},
	jsonAlreadyExists:function(o,l){
		if(o[l]!==undefined){
			var co=2
			while(o[l+"_"+co]!==undefined){co++}
			var n=l+"_"+co
			var p='"'+l+'" Ja existe este objeto.\nDeseja renomea-lo? (otherwise the old "'+l+'" will be overwritten.)'
			p=prompt(p,n)
			if(p){l=p}
		}
		return l
	},
	jsonAddChild:function(f,label){
		var first=f.jbefore.checked
		var l=f.orgjlabelRNA.value
		eval('var o=this.'+l)
		var t=(o && this.isArray(o))?"array":typeof o
		if(t=="object"){
			var nl=label||prompt("Label (without path):","")
			if(!nl){return}
			if(nl/1==nl){nl="$"+nl}
			nl=this.jsonAlreadyExists(o,nl)
			var n=nl.replace(/\w/g,'')===""?l+"."+nl:l+'["'+nl+'"]'
			eval('this.'+n+'={}')
			if(first){
				eval("var t=this."+l+";this."+l+"={};var s=this."+l)
				eval('this.'+n+'={}')
				for(var i in t){s[i]=t[i]}				
			}
		}
		if(t=="array"){
			o.push({})
			n=l+"["+(o.length-1)+"]"
			if(first){
				for(var i=o.length-1;i>0;i--){o[i]=o[i-1]}
				o[0]={}
				n=l+"[0]"
			}
		}
		this.JSONbuild(this.baseDiv,this.json)
		for(var i in this.stateMem){this.openAndClose(i,true)}
		this.jsonResponder(n)
	},
	jsonAddSibling:function(f,label){
		var before=f.jbefore.checked
		var l=f.orgjlabelRNA.value
		var r=Math.random()
		eval('var temp=this.'+l)
		eval('this.'+l+"=r")
		var s=this.JSONstring.make(this.json)
		s=s.split(r+",")
		if(s.length<2){s=s[0].split(r)}
		var lp=this.jsonParent(l)
		eval('var o=this.'+lp)
		var t=(o && this.isArray(o))?"array":typeof o
		if(t=="object"){
			var nl=label||prompt("Label (without path):","")
			if(!nl){return}
			if(nl/1==nl){nl="$"+nl}
			nl=this.jsonAlreadyExists(o,nl)
			var n=nl.replace(/\w/g,'')===""?"."+nl:'["'+nl+'"]'
			s=s.join('null,"'+nl+'":{},')
			lp+=n
		}
		if(t=="array"){
			s=s.join('null,{},')
			var k=l.split("[")
			k[k.length-1]=(k[k.length-1].split("]").join("")/1+1)+"]"
			lp=k.join("[")
		}
		s=s.split("},}").join("}}") // replace with something better soon
		eval('this.json='+s)
		eval('this.'+l+'=temp')
		if(before){lp=this.jsonSwitchPlace(this.jsonParent(l),l,lp)}
		this.JSONbuild(this.baseDiv,this.json)
		for(var i in this.stateMem){this.openAndClose(i,true)}
		this.jsonResponder(lp)
	},
	jSaveFirst:function(f,a){
		var l=f.orgjlabelRNA.value
		eval("var orgj=this."+l)
		orgj=this.JSONstring.make(orgj)
		var v=f.jvalueRNA.value
		v=f.jtypeRNA.value=="string"?this.JSONstring.make(v):v
		v=v.split("\r").join("")
		if(orgj!=v || f.orgjlabelRNA.value!=f.jlabelRNA.value || this.jTypeChanged){
			var k=confirm("Save before "+a+"?")
			if(k){this.jsonChange(f)}
		}
	},
	jsonRename:function(f){
		this.jSaveFirst(f,"renaming")
		var orgl=l=f.orgjlabelRNA.value
		l=this.jsonChild(l)
		var nl=prompt("Label (without path):",l)
		if(!nl){return}
		this.jsonResponder(orgl)
		var nl=nl.replace(/\w/g,'')===""?"."+nl:'["'+nl+'"]'
		f.jlabelRNA.value=this.jsonParent(orgl)+nl
		this.jsonChange(f,false,true)
	},
	jsonSwitchPlace:function(p,el1,el2){
		var orgel1=el1, orgel2=el2
		eval("var o=this."+p)
		if(this.isArray(o)){
			eval("var t=this."+el1)
			eval("this."+el1+"=this."+el2)
			eval("this."+el2+"=t")
			return orgel1
		}
		el1=this.jsonChild(el1)
		el2=this.jsonChild(el2)
		var o2={}
		for(var i in o){
			if(i==el1){o2[el2]=o[el2];o2[el1]=o[el1];continue}
			if(i==el2){continue}
			o2[i]=o[i]
		}
		eval("this."+p+"=o2")
		return orgel2
	},
	jsonCut:function(f){
		this.jSaveFirst(f,"cutting")
		this.jsonCopy(f,true)
		this.jsonChange(f,true)
		this.setJsonMessage('Recortado para memoria!')
	},
	jsonCopy:function(f,r){
		if(!r){this.jSaveFirst(f,"copying")}
		var l=f.orgjlabelRNA.value
		eval("var v=this."+l)
		v=this.JSONstring.make(v)
		var l=this.jsonChild(l)
		this.jClipboard={label:l,jvalueRNA:v}
		this.jsonResponder(f.jlabelRNA.value)
		if(!r){this.setJsonMessage('Copiado para memoria!')}
	},
	jsonPaste:function(f,r){
		var t=f.jtypeRNA.value
		var sibling=t!="object" && t!="array"
		if(!f.jPasteAsChild.checked){sibling=true}
		if(f.orgjlabelRNA.value=="json"){sibling=false}
		if(sibling){this.jsonAddSibling(f,this.jClipboard.label)}
		else {this.jsonAddChild(f,this.jClipboard.label)}
		var l=f.orgjlabelRNA.value
		eval("this."+l+"="+this.jClipboard.jvalueRNA)
		this.jsonResponder(l)
		this.jsonChange(f)
		if(!r){this.setJsonMessage('Colado com Sucesso!')}
	},
	setJsonMessage:function(x){
		this.$('jformMessageRNA').innerHTML=x
		setTimeout("treeBuilderRNA.$('jformMessageRNA').innerHTML=''",1500)
	},
	changeJsonDataType:function(x,f){
		this.jTypeChanged=true
		var v=f.jvalueRNA.value
		var orgv=v;
		v=x=='object'?'{"label":"'+v+'"}':v
		v=x=='array'?'["'+v+'"]':v
		if(!orgv){
			v=x=='object'?'{}':v
			v=x=='array'?'[]':v
		}
		v=x=='function'?'function(){'+v+'}':v
		v=x=='string'?v:v
		v=x=='number'?v/1:v
		v=x=='boolean'?!!v:v
		v=x=='null'?'null':v
		v=x=='undefined'?'undefined':v
		f.jvalueRNA.value=v		
	},
	jsonChange:function(f,remove,rename){
		try {
			var l=f.jlabelRNA.value
			var orgl=f.orgjlabelRNA.value||"json.not1r2e3a4l"
			eval("var cur=this."+l)
			if(l!=orgl && cur!==undefined){
				var c=confirm(l+"\n\nJa possui esta dado. Sobrescrever?")
				if(!c){return false}
			}
			var v=f.jvalueRNA.value.split("\r").join("")
			if(f.jtypeRNA.value=="string"){
				v=this.JSONstring.make(v)
			}
			if(l=="json"){
				eval("v="+v)
				this.JSONbuild(this.baseDiv,v)
				for(var i in this.stateMem){this.openAndClose(i,true)}
				this.setJsonMessage('Saved!')
				return false
			}
			eval("var json="+this.JSONstring.make(this.json))
			var randi=Math.random()
			eval(orgl+'='+randi)
			var paname=this.jsonParent(orgl)
			var samepa=this.jsonParent(orgl)==this.jsonParent(l)
			eval("var pa="+paname)
			if(this.isArray(pa)){	
				eval(paname+'=[];var newpa='+paname)
				for(var i=0;i<pa.length;i++){
					if(pa[i]!=randi){newpa[i]=pa[i]}
				}
				if(remove){
					var pos=l.substring(l.lastIndexOf("[")+1,l.lastIndexOf("]"))/1
					newpa=newpa.splice(pos,1)
				}
				if(!remove){eval(l+"="+v)}
			}		
			else {
				eval(paname+'={};var newpa='+paname)
				for(var i in pa){
					if(pa[i]!=randi){newpa[i]=pa[i]}
					else if(samepa && !remove){eval(l+"="+v)}
				}
				if(!samepa && !remove){eval(l+"="+v)}
			}
			this.json=json
			var selId=this.selectedElement?this.selectedElement.id:null
			this.JSONbuild(this.baseDiv,this.json)
			for(var i in this.stateMem){this.openAndClose(i,true)}
			this.selectedElement=this.$(selId)
			if(this.selectedElement && !remove && orgl!="json.not1r2e3a4l"){
				this.selectedElement.style.fontWeight="bold"
			}
			if(remove){l=""}
			this.setJsonMessage(remove?'Deleted!':rename?'Renomiado com Sucesso!':'Salvo com Sucesso!')
			if(!remove){this.jsonResponder(l)}
  		}
		catch(err){
			alert(err+"\n\n"+"Erro ao Salvar!")
		}
		return false
	},
	JSONbuild:function(divName,x,y,z){
		if(!z){
			this.partMem=[]
			this.JSONmem=[]
			this.json=x
			this.baseDiv=divName
		}
		var t=(x && this.isArray(x))?"array":typeof x
		y=y===undefined?"json":y
		z=z||0
		this.partMem[z]='["'+y+'"]'
		if(typeof y!="number" && y.replace(/\w/g,'')===""){this.partMem[z]="."+y}
		if(typeof y=="number"){this.partMem[z]="["+y+"]"}
		if(z===0){this.partMem[z]="json"}
		this.partMem=this.partMem.slice(0,z+1)
		var x2=x
		this.JSONmem.push({type:t,label:y,todo:this.partMem.join(""),level:z+1})
		if(t=="object"){
			for(var i in x){
				this.JSONbuild(false,x[i],i,z+1)
			}
		}
		if(t=="array"){
			for(var i=0;i<x.length;i++){
				this.JSONbuild(false,x[i],i,z+1)
			}
		}
		if(divName){
			this.build(divName,this.jsonResponder,this.JSONmem)
			if(!this.hasRunJSONbuildOnce){this.jsonResponder('json')}
			this.hasRunJSONbuildOnce=true
		}
	},
	build:function(divName,todoFunc,data){
		//
		// divName is the id of the div we'll build the tree inside
		//
		// todoFunc - a function to call on label click with todo as parameter
		//
		// data should be an array of objects
		// each object should contain label,todo + level or id and pid (parentId)
		//
		var d=data, n=divName, $=this.$, lastlevel=0, levelmem=[], im=this.images;
		this.treeBaseDiv=divName
		if(!d){
			var c=$(divName).childNodes;
			for(var i=0;i<c.length;i++){
				if((c[i].tagName+"").toLowerCase()=='pre'){d=this.preParse(c[i])}
			};
			if(!d){return}
		};
		$(n).style.display="none";
		while ($(n).firstChild){$(n).removeChild($(n).firstChild)};
		for(var i=0;i<d.length;i++){
			if(d[i].level && !lastlevel){lastlevel=d[i].level};
			if(d[i].level && d[i].level>lastlevel){levelmem.push(n);n=d[i-1].id};
			if(d[i].level && d[i].level>lastlevel+1){return 'Trying to jump levels!'};
			if(d[i].level && d[i].level<lastlevel){
				for(var j=d[i].level;j<lastlevel;j++){n=levelmem.pop()}
			};
			if(!d[i].id){d[i].id=n+"_"+i};
			if(!d[i].pid){d[i].pid=n};
			lastlevel=d[i].level;
			var a=document.createElement('div');
			var t=document.createElement('span');
			t.style.verticalAlign='middle';
			a.style.whiteSpace='nowrap';
			var t2=document.createTextNode(d[i].label);
			t.appendChild(t2);
			a.style.paddingLeft=d[i].pid==divName?'0px':im.nodeWidth+'px';
			a.style.cursor='pointer';
			a.style.display=(d[i].pid==divName)?'':'none';
			a.id=d[i].id;
			a.t=t;
			var f=function(){
				var todo=d[i].todo;
				var func=todoFunc;
				a.onclick=function(e){
					if(treeBuilderRNA.maniClick=="giveItBack"){return todo}
					if(treeBuilderRNA.selectedElement){
						treeBuilderRNA.selectedElement.style.fontWeight=""
					}
					this.style.fontWeight="bold"
						treeBuilderRNA.selectedElement=this
					if(treeBuilderRNA.maniClick=="selectIt"){return}
					func(todo);
					if (!e){e=window.event};
					e.cancelBubble = true;
					if(e.stopPropagation){e.stopPropagation()};
				};
				a.onmouseover=function(e){
					//this.style.color="#999"
					if (!e){e=window.event};
					e.cancelBubble = true;
					if(e.stopPropagation){e.stopPropagation()};
				};
				a.onmouseout=function(e){
					//this.style.color=""
					if (!e){e=window.event};
					e.cancelBubble = true;
					if(e.stopPropagation){e.stopPropagation()};
				};
			};
			f();
			$(d[i].pid).appendChild(a);
			if(d[i].pid==divName && !a.previousSibling){a.first=true};
		};
		// calculate necessary element looks before initial display
		for(var i=0;i<d.length;i++){var x=$(d[i].id);if(x && x.style.display!="none"){this.setElementLook(x)}};
		$(divName).style.display="";
	},
	setElementLook:function(m){
		var $=this.$, im=this.images
		if(!m.inited){
			var co=0
			for(var j in im){
				if(!Object.prototype[j]){
					if(j=="vertLine"){break};
					var img=document.createElement('img');
					var k=(m.first && j.indexOf('Node')>=0)?j+'First':j;
					img.src=im.path+(im[k]?im[k]:k+'.gif');
					img.style.display="none";
					img.style.verticalAlign="middle";
					img.id=m.id+"_"+j;
					if(j.indexOf('folderNode')==0){
						img.onclick=function(e){
							treeBuilderRNA.openAndClose(this);
							if (!e){e=window.event};
							e.cancelBubble = true;
							if(e.stopPropagation){e.stopPropagation()};
						}
					};
					if(m.firstChild){m.insertBefore(img,m.childNodes[co]); co++}
					else {m.appendChild(img)};
				}
			};
			m.insertBefore(m.t,m.childNodes[co]);
			m.inited=true
		};
		var lastChild=m.childNodes[m.childNodes.length-1];
		var isParent=(lastChild.tagName+"").toLowerCase()=="div";
		var isLast=!m.nextSibling;
		var isOpen=isParent && lastChild.style.display!='none';
		$(m.id+"_folder").style.display=!isOpen && isParent?'':'none';
		$(m.id+"_folderOpen").style.display=isOpen && isParent?'':'none';
		$(m.id+"_doc").style.display=isParent?'none':'';
		$(m.id+"_docNode").style.display=isParent || isLast?'none':'';
		$(m.id+"_docNodeLast").style.display=isParent || !isLast?'none':'';
		$(m.id+"_folderNode").style.display=isOpen || !isParent || isLast?'none':'';
		$(m.id+"_folderNodeLast").style.display=isOpen || !isParent || !isLast?'none':'';
		$(m.id+"_folderNodeOpen").style.display=!isOpen || !isParent || isLast?'none':'';
		$(m.id+"_folderNodeOpenLast").style.display=!isOpen || !isParent || !isLast?'none':'';
		var p=m.parentNode.nextSibling;
		if(p && p.id){
			var sp=p;insideBase=false;
			while(sp){if(sp==$(this.treeBaseDiv)){insideBase=true};sp=sp.parentNode}
			if(!insideBase){return}
			var bg=im.path+(im.vertLine?im.vertLine:'vertLine.gif');
			m.style.backgroundImage='url('+bg+')';
			m.style.backgroundRepeat='repeat-y'
		};
	},
	openAndClose:function(x,remem){
		var o, div=remem?this.$(x):x.parentNode;
		if(!div){return}
		if(remem){o=this.stateMem[div.id]}
		else {o=x.id.indexOf('Open')<0}
		if(remem=="open"){o=true}
		this.stateMem[div.id]=o
		var c=div.childNodes;
		for(var i=0;i<c.length;i++){
			if(c[i].tagName.toLowerCase()!="div"){continue};
			c[i].style.display=o?'':'none';
			if(o && !c[i].inited){this.setElementLook(c[i])}
		};
		this.setElementLook(div)
	}
}



/*
JSONstring v 1.0
copyright 2006 Thomas Frank

Based on Steve Yen's implementation:
http://trimpath.com/project/wiki/JsonLibrary
*/

JSONeditorRNA.treeBuilderRNA.JSONstring={
	compactOutput:false, 		
	includeProtos:false, 	
	includeFunctions: true,
	detectCirculars:false,
	restoreCirculars:false,
	make:function(arg,restore) {
		this.restore=restore;
		this.mem=[];this.pathMem=[];
		return this.toJsonStringArray(arg).join('');
	},
	toObject:function(x){
		eval("this.myObj="+x);
		if(!this.restoreCirculars || !alert){return this.myObj};
		this.restoreCode=[];
		this.make(this.myObj,true);
		var r=this.restoreCode.join(";")+";";
		eval('r=r.replace(/\\W([0-9]{1,})(\\W)/g,"[$1]$2").replace(/\\.\\;/g,";")');
		eval(r);
		return this.myObj
	},
	toJsonStringArray:function(arg, out) {
		if(!out){this.path=[]};
		out = out || [];
		var u; // undefined
		switch (typeof arg) {
		case 'object':
			this.lastObj=arg;
			if(this.detectCirculars){
				var m=this.mem; var n=this.pathMem;
				for(var i=0;i<m.length;i++){
					if(arg===m[i]){
						out.push('"JSONcircRef:'+n[i]+'"');return out
					}
				};
				m.push(arg); n.push(this.path.join("."));
			};
			if (arg) {
				if (arg.constructor == Array) {
					out.push('[');
					for (var i = 0; i < arg.length; ++i) {
						this.path.push(i);
						if (i > 0)
							out.push(',\n');
						this.toJsonStringArray(arg[i], out);
						this.path.pop();
					}
					out.push(']');
					return out;
				} else if (typeof arg.toString != 'undefined') {
					out.push('{');
					var first = true;
					for (var i in arg) {
						if(!this.includeProtos && arg[i]===arg.constructor.prototype[i]){continue};
						this.path.push(i);
						var curr = out.length; 
						if (!first)
							out.push(this.compactOutput?',':',\n');
						this.toJsonStringArray(i, out);
						out.push(':');                    
						this.toJsonStringArray(arg[i], out);
						if (out[out.length - 1] == u)
							out.splice(curr, out.length - curr);
						else
							first = false;
						this.path.pop();
					}
					out.push('}');
					return out;
				}
				return out;
			}
			out.push('null');
			return out;
		case 'unknown':
		case 'undefined':
		case 'function':
			try {eval('var a='+arg)}
			catch(e){arg='function(){alert("Nao pode converter esta funcao em JSON, bug apenas encontrado no Safari. Esperamos que seja concertado em versoes futuras do Safari!")}'}
			out.push(this.includeFunctions?arg:u);
			return out;
		case 'string':
			if(this.restore && arg.indexOf("JSONcircRef:")==0){
				this.restoreCode.push('this.myObj.'+this.path.join(".")+"="+arg.split("JSONcircRef:").join("this.myObj."));
			};
			out.push('"');
			var a=['\n','\\n','\r','\\r','"','\\"'];
			arg+=""; for(var i=0;i<6;i+=2){arg=arg.split(a[i]).join(a[i+1])};
			out.push(arg);
			out.push('"');
			return out;
		default:
			out.push(String(arg));
			return out;
		}
	}
}