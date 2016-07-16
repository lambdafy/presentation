package srp;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SRPTest {

    @Test
    public void artist_with_empty_name_should_not_be_valid(){
        final SRP.functionalCombinatorSrp.ArtistService service = new SRP.functionalCombinatorSrp.ArtistService();

        Optional<SRP.functionalCombinatorSrp.Artist> artist = service.createArtistWithValidatedEmailAndName("", "random@rand.com");

        assertThat(artist, is(Optional.empty()));
    }

    @Test
    public void artist_with_invalid_mail_should_not_be_valid(){
        final SRP.functionalCombinatorSrp.ArtistService service = new SRP.functionalCombinatorSrp.ArtistService();

        Optional<SRP.functionalCombinatorSrp.Artist> artist = service.createArtistWithValidatedEmailAndName("Lenzman", "rand");

        assertThat(artist, is(Optional.empty()));
    }

    @Test
    public void artist_with_valid_mail_and_valid_name_should_be_valid(){
        final SRP.functionalCombinatorSrp.ArtistService service = new SRP.functionalCombinatorSrp.ArtistService();

        Optional<SRP.functionalCombinatorSrp.Artist> artist = service.createArtistWithValidatedEmailAndName("Lenzman", "rand@rand.com");

        assertThat(artist.isPresent(), is(true));
        assertThat(artist.get().name, is("Lenzman"));
        assertThat(artist.get().email, is("rand@rand.com"));
    }

}
