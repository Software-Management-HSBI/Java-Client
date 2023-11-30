import java.util.HashMap;

public class Constants {
	public static final HashMap<String, Integer> STARTCOLORS = new HashMap<String, Integer>() {
		{
			put("road", 0xFFFFFF);
			put("grass", 0xFFFFFF);
			put("rumble", 0xFFFFFF);
			put("lane", 0xFFFFFF);
		}
	};
	
	public static final HashMap<String, Integer> FINISHCOLORS = new HashMap<String, Integer>() {
        {
            put("road", 0x000000);
        	put("grass", 0x000000);
            put("rumble", 0x000000);
            put("lane", 0x000000);
        }
    };

	public static final HashMap<String, Integer> LIGHTCOLORS = new HashMap<String, Integer>() {
        {
            put("road", 0x6B6B6B);
            put("grass", 0x10AA10);
            put("rumble", 0x555555);
            put("lane", 0xCCCCCC);
        }
    };

	public static final HashMap<String, Integer> DARKCOLORS = new HashMap<String, Integer>() { 
		{
                put("road", 0x696969);
                put("grass", 0x009A00);
                put("rumble", 0xBBBBBB);
                put("lane", 0x696969);
        }
    };

	public static final HashMap<String, Integer> COLORS = new HashMap<String, Integer>() {
		{
			put("sky", 0x72D7EE);
			put("tree", 0x005108);
			put("fog", 0x005108);
		}
	};

	public static final String TEXTUREPATH = "src/main/resources/images/background/";
}
