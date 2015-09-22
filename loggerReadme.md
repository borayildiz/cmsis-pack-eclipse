#Logger
com.bora.logger plugin is just for examine the CMSIS-Pack Management for Eclipse
plugin behaviours. It logs real-time executed constructors, methods and stack info.
Currently, just some classes(CpPreferenceInitializer, CpPreferencePage etc.)
of cmsis-pack calls the log method. When you need to examine a method 
or constructor, you need to call related log method.

#Example Usage
1.) Run Eclipse application to test plugins.
2.) Open preferences page in Eclipse.
3.) Select CMSIS Packs.
4.) Close the preference page.
5.) In the LoggerView plugin click the Info button. You will see that which
constructors and methods are called when you open the CMSIS Packs page.
