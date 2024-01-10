

public abstract class Mode {


    Game game;




    public Mode(Game game){
        this.game = game;
    }






    static class SINGLEPLAYER extends Mode{


        public static Player player;

        public SINGLEPLAYER(Game game) {
            super(game);
        }
    }


    static class MULTIPLAYER extends Mode {

        public static Player player;
        public MULTIPLAYER(Game game) {
            super(game);
        }
    }

}
