package academy.service.image;

import academy.model.Image;
import academy.repository.img.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ImageService implements IImageService{
    @Autowired
    private IImageRepository imageRepository;
    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void delete(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Image> imageList) {
        imageRepository.saveAll(imageList);
    }
}
