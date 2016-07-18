import org.junit.Test;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdafyTestComposition {

    public class Artist {
        public String name;

        public Artist(String name) {
            this.name = name;
        }
    }

    public class Music {
        public Artist artist;

        public Music(Artist artist) {
            this.artist = artist;
        }
    }

    public class Track {
        public Music music;

        public Track(Music music) {
            this.music = music;
        }
    }

    public class Album {
        public List<Track> tracks;

        public Album(List<Track> tracks) {
            this.tracks = tracks;
        }
    }

    @Test
    public void simple_composition() {
        final Function<Integer, Integer> squared = a -> a * a;
        final Function<Integer, Integer> negated = a -> -a;
        final Function<Integer, Integer> squaredAndNegated = negated.compose(squared);
        squaredAndNegated.apply(4); // -16
    }


    @Test
    public void music_production_pipeline() {
        final Function<Artist, Music> writeSomeRandomMusic = Music::new;
        final Function<Music, Track> recordTheMusic = Track::new;
        final Function<Artist, Track> produceTrack = recordTheMusic.compose(writeSomeRandomMusic);
        //final Function<Artist, Track> produceTrack = writeSomeRandomMusic.andThen(recordTheMusic);
    }

    @Test
    public void music_production_pipeline2() {
        final Function<Artist, Music> writeSomeRandomMusic = Music::new;
        final Function<Music, Track> recordTheMusic = Track::new;
        final Function<List<Track>, Album> publishTheAlbum = Album::new;

        final Function<Artist, Track> produceTrack = recordTheMusic.compose(writeSomeRandomMusic);

        final Function<Artist, List<Track>> produceTracks =
                (artist) -> Stream.generate(() -> produceTrack.apply(artist)).limit(10).collect(Collectors.toList());
        final Function<Artist, Album> produceTheAlbum =  publishTheAlbum.compose(produceTracks);

        Artist someArtist = new Artist("Some Artist");
        Stream.generate(() -> 1).limit(10).mapToInt(i -> i).sum();  produceTheAlbum.apply(someArtist);
    }


}