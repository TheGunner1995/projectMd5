package academy.repository.img;

import academy.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {
}
