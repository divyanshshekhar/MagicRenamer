/*
* Mod
*
* v1.0.0.0
*
* 2017-April-5
*
* Copyright 2017 Divyansh Shekhar Gaur
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*     http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package DivyanshShekhar.MagicRenamer;

/**
* Datatype to store the modifictaion criteria. One object of this class to stores one criteria.
* @version 1.0.0.0
* @author Divyansh Shekhar Gaur
* @since 1.0.0.0
*/
public class Mod{
	String text;
	ModType type;
	
	/**
	* Converts the text entered by user into an object so that parameters can be easily used.
	* @param modText The string containg arguments passed by the user.
	* @since 1.0.0.0
	*/
	public Mod(String modText){
		if(modText.length()==0)
			return;
		//System.out.println(">>>modText = "+modText);
		text=modText.substring(1);
		// System.out.println(">>>text = "+text);
		//System.out.println("modText = "+modText+"\tText = "+modText+"modText = "+modText+"\n");
		char modType = modText.charAt(0);
		switch(modType){
			case '+':
			type = ModType.ADD;
			break;
			case '-':
			type = ModType.REMOVE;
			break;
			default:
				//System.out.println("ModType = "+modType+"\n");
				throw new IllegalArgumentException("Modification criteria is invalid.");
		}
	}
}