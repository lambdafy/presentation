package srp;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import static srp.SRP.functionalCombinatorSrp.ArtistValidator.*;

public class SRP {

    public interface noSrp{
        class Artist {
            private final String name;
            private final String email;

            public Artist(String name, String email) {
                this.name = name;
                this.email = email;
            }

            public String getName() {
                return name;
            }

            public String getEmail() {
                return email;
            }

            public boolean isValid() {
                return email.contains("@");
            }
        }
    }

    public interface Srp{
        class Artist {
            public final String name;
            public final Email email;
            public Artist(String name, Email email) {
                this.name = name;
                this.email = email;
            }
        }

        class Email {
            private final String email;

            public Email(String eMail) {
                this.email = eMail;
            }

            public boolean isValid(){
                return email.contains("@");
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Email email1 = (Email) o;
                return Objects.equals(email, email1.email);
            }

            @Override
            public int hashCode() {
                return Objects.hash(email);
            }
        }

        class ArtistService {
            public Optional<Artist> createValidArtist(String name, Email mail){
                if(mail.isValid()) return Optional.of(new Artist(name, mail));
                return Optional.empty();
            }
        }
    }

    public interface functionalSrp{
        class Artist {
            public final String name;
            public final String email;
            public Artist(String name, String email) {
                this.name = name;
                this.email = email;
            }
        }

        class ArtistService {
            public Optional<Artist> createArtistWithValidatedEmail(String name, String mail){
                return Optional
                        .of(new Artist(name, mail))
                        .flatMap(artist -> mailContainsAtSign(artist));
            }

            public Optional<Artist> createArtistWithValidatedEmailAndName(String name, String mail){
                return Optional
                        .of(new Artist(name, mail))
                        .flatMap(this::mailContainsAtSign)
                        .flatMap(this::nameIsNotEmpty);
            }

            private Optional<Artist> mailContainsAtSign(Artist artist){
                return artist.email.contains("@")?Optional.of(artist):Optional.empty();
            }

            private Optional<Artist> nameIsNotEmpty(Artist artist) {
                return artist.name.trim().length()>0?Optional.of(artist):Optional.empty();
            }
        }
    }

    public interface functionalCombinatorSrp{
        class Artist {
            public final String name;
            public final String email;
            public Artist(String name, String email) {
                this.name = name;
                this.email = email;
            }
        }

        interface ArtistValidator extends Function<Artist, Optional<Artist>>{
            static ArtistValidator hasMailWithAtSign(){
                return holds(artist -> artist.email.contains("@"));
            }

            static ArtistValidator nameIsNotEmpty(){
                return holds(artist -> artist.name.trim().length()>0);
            }

            static ArtistValidator holds(Function<Artist, Boolean> p){
                return artist -> p.apply(artist)?Optional.of(artist):Optional.empty();
            }

            default ArtistValidator and(ArtistValidator other){
                return artist -> apply(artist).flatMap(other);
            }
        }

        class ArtistService{
            public Optional<Artist> createArtistWithValidatedEmailAndName(String name, String mail){
                final ArtistValidator validator = nameIsNotEmpty().and(hasMailWithAtSign());
                return validator.apply(new Artist(name, mail));
            }
        }
    }

}
