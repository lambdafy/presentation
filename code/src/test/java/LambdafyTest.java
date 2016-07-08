import org.junit.Test;

import java.util.function.Function;

public class LambdafyTest {

    @FunctionalInterface
    public interface Artist{
        String name();
    }

    @FunctionalInterface
    public interface Label extends Function<Artist, Music>{
        default Music apply(Artist artist){
            return produceWith(artist);
        }

        Music produceWith(Artist artist);
    }

    class HospitalRecords implements Label{
        @Override
        public Music produceWith(Artist artist) {
            return new DrumAndBass(artist);
        }
    }

    interface Music {
        void play();
    }

    class DrumAndBass implements Music{
        private Artist artist;

        public DrumAndBass(Artist artist){
            this.artist = artist;
        }

        @Override
        public void play() {
            System.out.println(String.format("Producing Drum and Bass with famous %s.", artist.name()));
        }
    }

    @Test
    public void produce_old_style_java_music(){
        final Label label = new HospitalRecords();

        final Music music = label.produceWith(new Artist() {
            @Override
            public String name() {
                return "Lenzman";
            }
        });

        music.play();
    }

    @Test
    public void produce_lambdafied_java_music(){
        final Label label = artist -> new DrumAndBass(artist);

        final Music music = label.produceWith(() -> "Lenzman");

        music.play();
    }

    @Test
    public void produce_method_reference_java_music(){
        final Label label = DrumAndBass::new;

        final Music music = label.apply(() -> "Lenzman");

        music.play();
    }

    @Test
    public void produce_method_reference_functional_java_music(){
        final Function<Artist, Music> label = DrumAndBass::new;

        final Music music = label.apply(() -> "Lenzman");

        music.play();
    }
}
