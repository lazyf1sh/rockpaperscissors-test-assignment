
for /l %%x in (1, 1, 5) do (
	start telnet.exe localhost 27015
	cscript bot.vbs 
)



@PAUSE
