package academy.service.post;

import academy.model.Post;
import academy.service.IGenericService;

public interface IPostService extends IGenericService<Post, Long> {
    Iterable<Post> showPost();
}
