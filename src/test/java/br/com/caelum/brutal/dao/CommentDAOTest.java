package br.com.caelum.brutal.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.caelum.brutal.integracao.dao.DatabaseTestCase;
import br.com.caelum.brutal.model.Answer;
import br.com.caelum.brutal.model.Comment;
import br.com.caelum.brutal.model.CommentAndSubscribedUser;
import br.com.caelum.brutal.model.Question;
import br.com.caelum.brutal.model.User;

public class CommentDAOTest extends DatabaseTestCase {

    @Test
    public void should_find_recent_comments_and_subscribed_users() {
        CommentDAO comments = new CommentDAO(session);
        
        User questionAuthor = new User("question author", "qauthor@gmail", "1234");
        User otherQuestionAuthor = new User("question author", "otherqauthor@gmail", "1234");
        User answerAuthor = new User("answer author", "aauthor@gmail", "1234");
        User commentAuthor = new User("comment author", "cauthor@gmail", "1234");
        
        Question question = new Question("question question question question", "description description description description description", questionAuthor);
        Answer answer = new Answer("blablablablablablablablablabla", question, answerAuthor);
        
        Question otherQuestion = new Question("question question question question", "description description description description description", otherQuestionAuthor);
        Answer otherAnswer = new Answer("blablablablablablablablablabla", otherQuestion, answerAuthor);
        
        Comment comment = new Comment(commentAuthor, "commentcommentcommentcommentcomment");
        question.add(comment);
        
        session.save(questionAuthor);
        session.save(answerAuthor);
        session.save(commentAuthor);
        session.save(otherQuestionAuthor);
        session.save(comment);
        session.save(question);
        session.save(answer);
        session.save(otherQuestion);
        session.save(otherAnswer);
        
        List<CommentAndSubscribedUser> recentComments = comments.getRecentAnswersAndSubscribedUsers(3);
        
        assertEquals(2, recentComments.size());
        assertEquals(answerAuthor.getId(), recentComments.get(0).getUser().getId());
        assertEquals(comment.getId(), recentComments.get(0).getComment().getId());
        
        assertEquals(questionAuthor.getId(), recentComments.get(1).getUser().getId());
        assertEquals(comment.getId(), recentComments.get(1).getComment().getId());
        
        
    }

}
