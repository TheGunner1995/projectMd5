package academy.service.image;

import academy.model.Image;
import academy.service.IGenericService;

import java.util.List;

public interface IImageService extends IGenericService<Image, Long> {
    void saveAll(List<Image> imageList);
}
