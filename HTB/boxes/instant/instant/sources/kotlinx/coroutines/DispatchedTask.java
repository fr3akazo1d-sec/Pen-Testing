package kotlinx.coroutines;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.scheduling.Task;
import kotlinx.coroutines.scheduling.TaskContext;

/* compiled from: DispatchedTask.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u000e\b \u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00060\u0002j\u0002`\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001f\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0010¢\u0006\u0002\b\u0011J\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0010¢\u0006\u0002\b\u0014J\u001f\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0010¢\u0006\u0004\b\u0016\u0010\u0017J\u001a\u0010\u0018\u001a\u00020\f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u00102\b\u0010\u001a\u001a\u0004\u0018\u00010\u0010J\u0006\u0010\u001b\u001a\u00020\fJ\u000f\u0010\u001c\u001a\u0004\u0018\u00010\u000eH ¢\u0006\u0002\b\u001dR\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX \u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lkotlinx/coroutines/DispatchedTask;", "T", "Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/SchedulerTask;", "resumeMode", "", "(I)V", "delegate", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "cancelCompletedResult", "", "takenState", "", "cause", "", "cancelCompletedResult$kotlinx_coroutines_core", "getExceptionalResult", "state", "getExceptionalResult$kotlinx_coroutines_core", "getSuccessfulResult", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "handleFatalException", "exception", "finallyException", "run", "takeState", "takeState$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public abstract class DispatchedTask<T> extends Task {
    public int resumeMode;

    public void cancelCompletedResult$kotlinx_coroutines_core(Object takenState, Throwable cause) {
    }

    public abstract Continuation<T> getDelegate$kotlinx_coroutines_core();

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object state) {
        return state;
    }

    public abstract Object takeState$kotlinx_coroutines_core();

    public DispatchedTask(int i) {
        this.resumeMode = i;
    }

    public Throwable getExceptionalResult$kotlinx_coroutines_core(Object state) {
        CompletedExceptionally completedExceptionally = state instanceof CompletedExceptionally ? (CompletedExceptionally) state : null;
        if (completedExceptionally != null) {
            return completedExceptionally.cause;
        }
        return null;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object m148constructorimpl;
        UndispatchedCoroutine<?> undispatchedCoroutine;
        Object m148constructorimpl2;
        CancellationException cancellationException;
        if (DebugKt.getASSERTIONS_ENABLED() && this.resumeMode == -1) {
            throw new AssertionError();
        }
        TaskContext taskContext = this.taskContext;
        try {
            DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) getDelegate$kotlinx_coroutines_core();
            Continuation<T> continuation = dispatchedContinuation.continuation;
            Object obj = dispatchedContinuation.countOrElement;
            CoroutineContext context = continuation.getContext();
            Object updateThreadContext = ThreadContextKt.updateThreadContext(context, obj);
            if (updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS) {
                undispatchedCoroutine = CoroutineContextKt.updateUndispatchedCompletion(continuation, context, updateThreadContext);
            } else {
                undispatchedCoroutine = null;
            }
            try {
                CoroutineContext context2 = continuation.getContext();
                Object takeState$kotlinx_coroutines_core = takeState$kotlinx_coroutines_core();
                Throwable exceptionalResult$kotlinx_coroutines_core = getExceptionalResult$kotlinx_coroutines_core(takeState$kotlinx_coroutines_core);
                Job job = (exceptionalResult$kotlinx_coroutines_core == null && DispatchedTaskKt.isCancellableMode(this.resumeMode)) ? (Job) context2.get(Job.INSTANCE) : null;
                if (job != null && !job.isActive()) {
                    CancellationException cancellationException2 = job.getCancellationException();
                    cancelCompletedResult$kotlinx_coroutines_core(takeState$kotlinx_coroutines_core, cancellationException2);
                    Result.Companion companion = Result.INSTANCE;
                    if (DebugKt.getRECOVER_STACK_TRACES() && (continuation instanceof CoroutineStackFrame)) {
                        cancellationException = StackTraceRecoveryKt.recoverFromStackFrame(cancellationException2, (CoroutineStackFrame) continuation);
                        continuation.resumeWith(Result.m148constructorimpl(ResultKt.createFailure(cancellationException)));
                    }
                    cancellationException = cancellationException2;
                    continuation.resumeWith(Result.m148constructorimpl(ResultKt.createFailure(cancellationException)));
                } else if (exceptionalResult$kotlinx_coroutines_core != null) {
                    Result.Companion companion2 = Result.INSTANCE;
                    continuation.resumeWith(Result.m148constructorimpl(ResultKt.createFailure(exceptionalResult$kotlinx_coroutines_core)));
                } else {
                    Result.Companion companion3 = Result.INSTANCE;
                    continuation.resumeWith(Result.m148constructorimpl(getSuccessfulResult$kotlinx_coroutines_core(takeState$kotlinx_coroutines_core)));
                }
                Unit unit = Unit.INSTANCE;
                try {
                    Result.Companion companion4 = Result.INSTANCE;
                    DispatchedTask<T> dispatchedTask = this;
                    taskContext.afterTask();
                    m148constructorimpl2 = Result.m148constructorimpl(Unit.INSTANCE);
                } catch (Throwable th) {
                    Result.Companion companion5 = Result.INSTANCE;
                    m148constructorimpl2 = Result.m148constructorimpl(ResultKt.createFailure(th));
                }
                handleFatalException(null, Result.m151exceptionOrNullimpl(m148constructorimpl2));
            } finally {
                if (undispatchedCoroutine == null || undispatchedCoroutine.clearThreadContext()) {
                    ThreadContextKt.restoreThreadContext(context, updateThreadContext);
                }
            }
        } catch (Throwable th2) {
            try {
                Result.Companion companion6 = Result.INSTANCE;
                taskContext.afterTask();
                m148constructorimpl = Result.m148constructorimpl(Unit.INSTANCE);
            } catch (Throwable th3) {
                Result.Companion companion7 = Result.INSTANCE;
                m148constructorimpl = Result.m148constructorimpl(ResultKt.createFailure(th3));
            }
            handleFatalException(th2, Result.m151exceptionOrNullimpl(m148constructorimpl));
        }
    }

    public final void handleFatalException(Throwable exception, Throwable finallyException) {
        if (exception == null && finallyException == null) {
            return;
        }
        if (exception != null && finallyException != null) {
            kotlin.ExceptionsKt.addSuppressed(exception, finallyException);
        }
        if (exception == null) {
            exception = finallyException;
        }
        Intrinsics.checkNotNull(exception);
        CoroutineExceptionHandlerKt.handleCoroutineException(getDelegate$kotlinx_coroutines_core().getContext(), new CoroutinesInternalError("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", exception));
    }
}